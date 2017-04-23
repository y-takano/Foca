package jp.gr.java_conf.ke.foca.adapter;

import jp.gr.java_conf.ke.namespace.foca.BindAttr;
import jp.gr.java_conf.ke.namespace.foca.ItemBind;

/**
 * Created by YT on 2017/04/22.
 */

public abstract class AbstractConverter<IN, OUT, PARAM> implements ParamTypeConverter<IN, OUT> {

    @Override
    public final OUT convert(IN input, OUT output, ItemBind bind) throws Exception {
        BindAttr fromDef = bind.getFrom();
        String getterName = fromDef.getGetter();
        String getfieldName = fromDef.getField();
        PARAM fromData = selectParameter(input, getterName, getfieldName);

        BindAttr toDef = bind.getTo();
        String setterName = toDef.getSetter();
        String setfieldName = toDef.getField();
        setParameter(output, fromData, setterName, setfieldName);
        return output;
    }

    protected abstract PARAM selectParameter(
            final IN source, String getterName, String fieldName)
            throws Exception;

    protected abstract void setParameter(
            final OUT dist, final PARAM param, String setterName, String fieldName)
            throws Exception;
}
