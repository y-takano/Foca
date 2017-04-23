package jp.gr.java_conf.ke.foca.adapter;

import jp.gr.java_conf.ke.namespace.foca.ItemBind;

/**
 * Created by YT on 2017/04/12.
 */

public interface ParamTypeConverter<IN, OUT> {

    OUT convert(IN input, OUT output, ItemBind cnvDef) throws Exception;
}
