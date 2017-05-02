package jp.gr.java_conf.ke.foca.internal;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.List;

import jp.gr.java_conf.ke.foca.DIContents;
import jp.gr.java_conf.ke.foca.FocaException;
import jp.gr.java_conf.ke.foca.aop.MethodAdvice;
import jp.gr.java_conf.ke.foca.internal.injector.Injector;
import jp.gr.java_conf.ke.foca.internal.injector.InjectorFactory;
import jp.gr.java_conf.ke.foca.internal.validator.Annotations;
import jp.gr.java_conf.ke.foca.internal.util.AspectWeaver;
import jp.gr.java_conf.ke.foca.internal.util.MethodAdvices;
import jp.gr.java_conf.ke.foca.internal.validator.Validator.EnabledMarker;
import jp.gr.java_conf.ke.namespace.foca.Joinpoint;
import jp.gr.java_conf.ke.util.Reflection;

/**
 * Created by YT on 2017/04/10.
 */

public class InjectService {

    public void execute(InjectRequest request) throws FocaException {
        Object targetInstance = request.getTarget().getInstance();
        try {
            InjectState state = request.getState();
            Field[] fields = targetInstance.getClass().getDeclaredFields();

            // @Gatewayの収集
            Collection<EnabledMarker> gateway = Annotations.collect(fields,
                    InjectionOrder.Gateway).validate();
            if (!gateway.isEmpty()) {
                // Endpointを起動
                if (state.isCallable(InjectionOrder.Entrypoint))
                    startEntrypointService(gateway, request);
                // Dataflow注入実行
                if (state.isInjectable(InjectionOrder.Gateway))
                    inject(gateway, request);
            }

            // @Controller, @Presenterの収集
            Collection<EnabledMarker> flow = Annotations.collect(fields,
                    InjectionOrder.Controller,
                    InjectionOrder.Presenter).validate();
            if (!flow.isEmpty()) {
                // Endpointを起動
                if (state.isCallable(InjectionOrder.Entrypoint))
                    startEntrypointService(flow, request);
                // Dataflow注入実行
                if (state.isInjectable(InjectionOrder.Controller)
                        || state.isInjectable(InjectionOrder.Presenter))
                    inject(flow, request);
            }

            // plugin型Adapterの@InputPort, @View, @Driverを収集
            Collection<EnabledMarker> plugin = Annotations.collect(fields,
                    InjectionOrder.Inputport,
                    InjectionOrder.View,
                    InjectionOrder.Driver).validate();
            if (!plugin.isEmpty()) {
                // Adapter注入実行
                if (state.isInjectable(InjectionOrder.Inputport)
                        || state.isInjectable(InjectionOrder.View)
                        || state.isInjectable(InjectionOrder.Driver))
                    inject(plugin, request);
            }

            // @Logの収集
            Collection<EnabledMarker> log = Annotations.collect(fields,
                    InjectionOrder.Log).validate();
            if (!log.isEmpty()) {
                // Log注入実行
                if (state.isInjectable(InjectionOrder.Log)) inject(log, request);
            }

        } catch (FocaException e) {
            e.setURL(request.getSourceURL());
            e.setDITarget(targetInstance);
            throw e;
        }
    }

    private void startEntrypointService(
            Iterable<EnabledMarker> component, InjectRequest request) throws FocaException {

        DIContents contents = request.getContents();
        Joinpoint joinpoint = null;
        for (EnabledMarker marker : component) {
            String name = Annotations.getFlowName(marker.annotation());
            if (name == null) continue;
            joinpoint = contents.selectEntrypoint(name);
        }

        // EntryPoint未定義は正常。
        if (joinpoint == null) return;

        // EntryPointが2つ以上存在時はXSD設計と矛盾するため内部異常
        final Object target = Reflection.newInstance(joinpoint.getInject().getClazz());
        List<EnabledMarker> entrypoint = Annotations.collect(target.getClass().getDeclaredMethods(),
                InjectionOrder.Entrypoint).validate();
        if (entrypoint.size() < 1 || 1 < entrypoint.size()) {
            throw new InternalError("xsdと処理結果が矛盾。");
        }

        // EntryPointへLogを注入
        InjectState state = request.getState();
        Collection<EnabledMarker> log =
                Annotations.collect(target.getClass().getDeclaredFields(),
                InjectionOrder.Log).validate();
        if (state.isInjectable(InjectionOrder.Log)) inject(log, request);

        // AspectWeaverを生成
        List<MethodAdvice> advices =
                MethodAdvices.asList(joinpoint, contents.aspecters());
        final AspectWeaver weaver = new AspectWeaver(target, advices);
        final Method method = entrypoint.get(0).method();

        // 別スレッドを起動しAspectWeaverを呼び出す
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    weaver.invoke(method, null);
                } catch (Throwable th) {
                    th.printStackTrace();
                }
            }
        });
        thread.setName("Foca-EntrypointService-Thread");
        thread.setDaemon(true);
        thread.start();
    }

    private void inject(Iterable<EnabledMarker> component, InjectRequest request) throws FocaException {
        Object targetInstance = request.getTarget().getInstance();
        DIContents contents = request.getContents();
        InjectorFactory factory = new InjectorFactory(request);
        for (EnabledMarker marker : component) {
            Injector injector = factory.create(marker.annotation());
            injector.inject(targetInstance, marker.field());
        }
    }

}
