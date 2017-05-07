package jp.gr.java_conf.ke.foca.internal.injector;

import java.lang.annotation.Annotation;

import jp.gr.java_conf.ke.foca.DIContents;
import jp.gr.java_conf.ke.foca.annotation.exit.View;
import jp.gr.java_conf.ke.foca.DefinitionNotFound;
import jp.gr.java_conf.ke.foca.FocaException;
import jp.gr.java_conf.ke.foca.annotation.entrance.InterfaceAdapter;
import jp.gr.java_conf.ke.foca.internal.adapter.AdapterFactory;
import jp.gr.java_conf.ke.namespace.foca.Aspect;
import jp.gr.java_conf.ke.namespace.foca.Converter;
import jp.gr.java_conf.ke.namespace.foca.Presenter;

/**
 * Created by YT on 2017/03/26.
 */

class PresenterInjector extends Injector {

    PresenterInjector(DIContents containts, String adapterName, AdapterFactory factory) {
        super(containts, adapterName, factory);
    }

    @Override
    InterfaceAdapter<?, ?> getAdapter(DIContents contents, String presenterName)
            throws FocaException {

        Presenter presenter = contents.selectPresenter(presenterName).rawValue();
        Class<? extends Annotation> inputPortClass = View.class;
        if (presenter == null) {
            throw new DefinitionNotFound(getFieldName(), presenterName, inputPortClass);
        }

        Converter cnv = presenter.getConverter();
        Iterable<Aspect> aspects = contents.aspecters();
        return factory().createAdapter(
                cnv, inputPortClass, presenter.getView(), aspects);
    }
}
