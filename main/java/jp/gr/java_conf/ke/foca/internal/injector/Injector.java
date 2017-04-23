package jp.gr.java_conf.ke.foca.internal.injector;

import java.lang.reflect.Field;

import jp.gr.java_conf.ke.foca.DIContents;
import jp.gr.java_conf.ke.foca.FocaException;
import jp.gr.java_conf.ke.foca.adapter.InterfaceAdapter;
import jp.gr.java_conf.ke.util.Reflection;

/**
 * Created by YT on 2017/03/26.
 */

public abstract class Injector {

    private String fieldName;
    private String adapterName;
    private DIContents contents;

    Injector(DIContents containts, String adapterName) {
        this.adapterName = adapterName;
        this.contents = containts;
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

    abstract InterfaceAdapter<?, ?> getAdapter(DIContents containts, String adapterName) throws FocaException;

    public void inject(Object injectee, Field targetField)
            throws FocaException {
        fieldName = targetField.getName();
        InterfaceAdapter<?, ?> adapter = getAdapter(contents, adapterName);
        Reflection.setFieldValue(injectee, fieldName, adapter);
    }
}
