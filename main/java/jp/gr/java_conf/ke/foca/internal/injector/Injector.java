package jp.gr.java_conf.ke.foca.internal.injector;

import java.lang.reflect.Field;

import jp.gr.java_conf.ke.foca.DIContents;
import jp.gr.java_conf.ke.foca.FocaException;
import jp.gr.java_conf.ke.foca.annotation.entrance.InterfaceAdapter;
import jp.gr.java_conf.ke.foca.internal.adapter.AdapterFactory;
import jp.gr.java_conf.ke.util.Reflection;

/**
 * Created by YT on 2017/03/26.
 */

public abstract class Injector {

    private String fieldName;
    private String adapterName;
    private DIContents contents;
    private AdapterFactory factory;

    Injector(DIContents containts, String adapterName, AdapterFactory factory) {
        this.adapterName = adapterName;
        this.contents = containts;
        this.factory = factory;
    }

    DIContents getContents() {
        return contents;
    }

    String getName() {
        return adapterName;
    }

    protected String getFieldName() {
        return fieldName;
    }

    protected AdapterFactory factory() {
        return factory;
    }

    abstract InterfaceAdapter<?, ?> getAdapter(DIContents containts, String adapterName) throws FocaException;

    public void inject(Object injectee, Field targetField)
            throws FocaException {
        fieldName = targetField.getName();
        InterfaceAdapter<?, ?> adapter = getAdapter(contents, adapterName);
        Reflection.setFieldValue(injectee, targetField, adapter);
    }
}
