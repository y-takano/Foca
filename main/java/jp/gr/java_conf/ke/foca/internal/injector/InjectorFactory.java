package jp.gr.java_conf.ke.foca.internal.injector;

import java.lang.annotation.Annotation;

import jp.gr.java_conf.ke.foca.DIContents;
import jp.gr.java_conf.ke.foca.FocaException;
import jp.gr.java_conf.ke.foca.internal.InjectRequest;
import jp.gr.java_conf.ke.foca.internal.InjectionOrder;
import jp.gr.java_conf.ke.foca.internal.adapter.AdapterFactory;
import jp.gr.java_conf.ke.foca.internal.validator.Annotations;

/**
 * Created by YT on 2017/04/22.
 */

public class InjectorFactory {

    private InjectRequest request;

    public InjectorFactory(InjectRequest request) {
        this.request = request;
    }

    public Injector create(Annotation anno) throws FocaException {
        Injector injector;
        DIContents contents = request.getContents();
        String flowName = Annotations.getFlowName(anno);
        String logName = Annotations.getLogName(anno);
        AdapterFactory factory = new AdapterFactory(request);
        switch(InjectionOrder.valueOf(anno)) {
            case Controller:
                injector = new ControllerInjector(contents, flowName, factory);
                break;

            case Presenter:
                injector = new PresenterInjector(contents, flowName, factory);
                break;

            case Gateway:
                injector = new GatewayInjector(contents, flowName, factory);
                break;

            case Log:
                injector = new LoggerInjector(contents, logName);
                break;

            default:
                throw new InternalError();
        }
        return injector;
    }
}
