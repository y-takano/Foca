package jp.gr.java_conf.ke.foca.internal;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.Collection;

import jp.gr.java_conf.ke.foca.FocaException;
import jp.gr.java_conf.ke.foca.FocaException.ErrorCode;
import jp.gr.java_conf.ke.foca.InjectException;
import jp.gr.java_conf.ke.foca.internal.injector.Injector;
import jp.gr.java_conf.ke.foca.internal.injector.InjectorFactory;
import jp.gr.java_conf.ke.foca.internal.validator.Annotations;
import jp.gr.java_conf.ke.foca.internal.validator.Validator;
import jp.gr.java_conf.ke.foca.internal.validator.Validator.EnabledMarker;

/**
 * Created by YT on 2017/04/10.
 */

public class InjectService {

    private enum Pattern {
        GATEWAY,
        CONTROLLER,
        PRESENTER,
        PLUGINS,
        LOG,
    }

    public void execute(InjectRequest request) throws FocaException {

        FocaException exception = null;
        EntrypointStartService epSvc = new EntrypointStartService();

        // 1. @Gatewayの注入
        exception = injectByPattern(Pattern.GATEWAY, request, epSvc, exception);

        // 2. @Controllerの注入
        exception = injectByPattern(Pattern.CONTROLLER, request, epSvc, exception);

        // 3. @Presenterの注入
        exception = injectByPattern(Pattern.PRESENTER, request, epSvc, exception);

        // 4. @InputPort, @View, @Driver(plugin型Adapter)の注入
        exception = injectByPattern(Pattern.PLUGINS, request, epSvc, exception);

        // 5. @Logの注入
        exception = injectByPattern(Pattern.LOG, request, epSvc, exception);

        // 6. 例外を通知
        InjectState state = request.getState();
        if (exception != null) {
            URL url = request.getSourceURL();
            Object target = state.getTarget();
            exception.setURL(url);
            exception.setDITarget(target);
            throw exception;
        }

        // 7. @EntryPoint起動
        epSvc.start();

        // 8. 処理完了を通知
        state.complete();
    }

    private FocaException injectByPattern(
            Pattern pattern, InjectRequest request,
            EntrypointStartService epSvc, FocaException exception) {
        InjectState state = request.getState();
        Object targetInstance = state.getTarget();
        Field[] fields = targetInstance.getClass().getDeclaredFields();
        try {
            // アノテーションフィールドの収集
            Collection<EnabledMarker> collection = collect(pattern, fields);
            if (!collection.isEmpty()) {

                // Entrypointの準備
                try {
                    standbyEntryPoint(pattern, collection, epSvc, request);
                } catch (FocaException e) {
                    e.setErrorCode(getErrorCode(pattern, true));
                    if (exception != null) e.setNext(exception);
                    exception = e;

                } catch (Exception e) {
                    FocaException fe = new InjectException(e);
                    fe.setErrorCode(getErrorCode(pattern, true));
                    if (exception != null) fe.setNext(exception);
                    exception = fe;
                }

                // 依存性の注入を実行
                if (isInjectable(pattern, state)) {
                    injectCore(collection, request);
                }
            }
        } catch (FocaException e) {
            e.setErrorCode(getErrorCode(pattern, false));
            if (exception != null) e.setNext(exception);
            exception = e;

        } catch (Exception e) {
            FocaException fe = new InjectException(e);
            fe.setErrorCode(getErrorCode(pattern, false));
            if (exception != null) fe.setNext(exception);
            exception = fe;
            exception.setErrorCode(getErrorCode(pattern, false));
        }
        return exception;
    }

    private Collection<EnabledMarker> collect(
            Pattern pattern, Field[] fields) throws FocaException {
        Validator v;
        switch(pattern) {
            case GATEWAY:
                v = Annotations.collect(fields,
                        InjectionOrder.Gateway);
                break;
            case CONTROLLER:
                v = Annotations.collect(fields,
                        InjectionOrder.Controller);
                break;
            case PRESENTER:
                v = Annotations.collect(fields,
                        InjectionOrder.Presenter);
                break;
            case PLUGINS:
                v = Annotations.collect(fields,
                        InjectionOrder.Inputport,
                        InjectionOrder.Driver,
                        InjectionOrder.View);
                break;
            case LOG:
                v = Annotations.collect(fields,
                        InjectionOrder.Log);
                break;
            default:
                throw new InternalError();
        }
        return v.validate();
    }

    private boolean isInjectable(Pattern pattern, InjectState state) {
        boolean ret;
        switch(pattern) {
            case GATEWAY:
                ret = state.isInjectable(InjectionOrder.Gateway);
                break;
            case CONTROLLER:
                ret = state.isInjectable(InjectionOrder.Controller);
                break;
            case PRESENTER:
                ret = state.isInjectable(InjectionOrder.Presenter);
                break;
            case PLUGINS:
                ret = state.isInjectable(InjectionOrder.Inputport)
                        || state.isInjectable(InjectionOrder.Driver)
                        || state.isInjectable(InjectionOrder.View);
                break;
            case LOG:
                ret = state.isInjectable(InjectionOrder.Log);
                break;
            default:
                throw new InternalError();
        }
        return ret;
    }

    private void standbyEntryPoint(
            Pattern pattern, Collection<EnabledMarker> collection,
            EntrypointStartService epSvc, InjectRequest request) throws FocaException {
        switch(pattern) {
            case GATEWAY:
            case CONTROLLER:
            case PRESENTER:
                epSvc.ready(collection, request);
                break;
            default:
                break;
        }
    }

    private ErrorCode getErrorCode(Pattern pattern, boolean isEntrypoint) {
        ErrorCode ret;
        switch(pattern) {
            case GATEWAY:
                ret = isEntrypoint ? ErrorCode.GTWY_ENTRY001 : ErrorCode.GTWY_INJECT001;
                break;
            case CONTROLLER:
                ret = isEntrypoint ? ErrorCode.CTRL_ENTRY001 : ErrorCode.CTRL_INJECT001;
                break;
            case PRESENTER:
                ret = isEntrypoint ? ErrorCode.PRST_ENTRY001 : ErrorCode.PRST_INJECT001;
                break;
            case PLUGINS:
                ret = ErrorCode.ADPT_INJECT001;
                break;
            case LOG:
                ret = ErrorCode.LOGR_INJECT001;
                break;
            default:
                ret = null;
                break;
        }
        return ret;
    }

    private void injectCore(Iterable<EnabledMarker> component, InjectRequest request) throws FocaException {
        Object targetInstance = request.getState().getTarget();
        InjectorFactory factory = new InjectorFactory(request);
        for (EnabledMarker marker : component) {
            Annotation anno = marker.annotation();
            Injector injector = factory.create(anno);
            injector.inject(targetInstance, marker.field());
            InjectionOrder order = InjectionOrder.valueOf(anno);
            request.getState().done(order);
        }
    }

}
