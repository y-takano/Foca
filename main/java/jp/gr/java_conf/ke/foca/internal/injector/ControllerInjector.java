package jp.gr.java_conf.ke.foca.internal.injector;

import java.lang.annotation.Annotation;

import jp.gr.java_conf.ke.foca.DIContents;
import jp.gr.java_conf.ke.foca.annotation.InputPort;
import jp.gr.java_conf.ke.foca.DefinitionNotFound;
import jp.gr.java_conf.ke.foca.FocaException;
import jp.gr.java_conf.ke.foca.adapter.InterfaceAdapter;
import jp.gr.java_conf.ke.foca.internal.adapter.AdapterFactory;
import jp.gr.java_conf.ke.namespace.foca.Aspect;
import jp.gr.java_conf.ke.namespace.foca.Controller;
import jp.gr.java_conf.ke.namespace.foca.Converter;

/**
 * Created by YT on 2017/03/26.
 */

class ControllerInjector extends Injector {

    ControllerInjector(DIContents containts, String adapterName) {
        super(containts, adapterName);
    }

    @Override
    InterfaceAdapter<?, ?> getAdapter(DIContents contents, String controllerName)
            throws FocaException {

        Controller ctrler = contents.selectController(controllerName);
        Class<? extends Annotation> inputPortClass = InputPort.class;
        if (ctrler == null) {
            throw new DefinitionNotFound(
                    getFieldName(), controllerName, inputPortClass);
        }

        Converter cnv = ctrler.getConverter();
        Iterable<Aspect> aspects = contents.aspecters();
        return new AdapterFactory()
                .createAdapter(cnv, inputPortClass, ctrler.getInputPort(), aspects);
    }
}
