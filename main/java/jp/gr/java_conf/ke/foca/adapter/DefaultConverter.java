package jp.gr.java_conf.ke.foca.adapter;

import jp.gr.java_conf.ke.util.Reflection;

/**
 * Created by YT on 2017/04/12.
 */

public class DefaultConverter<IN, OUT, PARAM> extends AbstractConverter<IN, OUT, PARAM> {

    @Override
    protected final PARAM selectParameter(IN source, String getterName, String fieldName) throws Exception {
        if (getterName == null) {
            if (fieldName == null) throw new InternalError();
            return getInputFromField(source, fieldName);
        }
        return getInputFromMethod(source, getterName);
    }

    @Override
    protected final void setParameter(OUT dist, PARAM data, String setterName, String fieldName) throws Exception {
        if (setterName == null) {
            if (fieldName == null) new InternalError();
            setParameterToField(dist, data, fieldName);
            return;
        }
        setParameterToMethod(dist, data, setterName);
    }

    protected PARAM getInputFromMethod(final IN model, String methodName) throws Exception {
        if (model == null) return null;
        return (PARAM) Reflection.callMethod(model, methodName, null);
    }

    protected PARAM getInputFromField(final IN model, String fieldName) throws Exception {
        if (model == null) return null;
        return Reflection.getFieldValue(model, fieldName);
    }

    protected void setParameterToMethod(final OUT model, final PARAM parameter, String methodName) throws Exception {
        if (model == null) return;
        Reflection.callMethod(model, methodName, parameter);
    }

    protected void setParameterToField(final OUT model, final PARAM parameter, String fieldName) throws Exception {
        if (model == null) return;
        Reflection.setFieldValue(model, fieldName, parameter);
    }

}
