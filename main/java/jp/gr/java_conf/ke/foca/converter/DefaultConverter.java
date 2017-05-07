package jp.gr.java_conf.ke.foca.converter;

import jp.gr.java_conf.ke.util.Reflection;

/**
 * データ変換インターフェースのデフォルト実装.<br>
 * <br>
 * バインド定義のconverterを指定しない場合に動作します。<br>
 * {@link AbstractConverter}を継承しています。<br>
 *
 * @param <IN> InterfaceAdapterの呼出元から渡されたパラメータ
 * @param <OUT> 出口メソッドに渡されるデータのインスタンス(生成済)
 * @param <PARAM> INパラメータから取得した型クラス
 * @see ParamTypeConverter
 * @see AbstractConverter
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
        return (PARAM) Reflection.callMethod(model, methodName);
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
