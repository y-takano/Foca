package jp.gr.java_conf.ke.foca.converter;

import jp.gr.java_conf.ke.namespace.foca.BindAttr;
import jp.gr.java_conf.ke.namespace.foca.ItemBind;

/**
 * データ変換インターフェースの抽象クラス.<br>
 * <br>
 * データ変換をカスタマイズしやすいように抽象化されたクラス.<br>
 *
 * @param <IN> InterfaceAdapterの呼出元から渡されたパラメータ
 * @param <OUT> 出口メソッドに渡されるデータのインスタンス(生成済)
 * @param <PARAM> INパラメータから取得した型クラス
 * @see ParamTypeConverter
 */
public abstract class AbstractConverter<IN, OUT, PARAM> implements ParamTypeConverter<IN, OUT> {

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
