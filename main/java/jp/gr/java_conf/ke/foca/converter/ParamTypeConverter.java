package jp.gr.java_conf.ke.foca.converter;

import jp.gr.java_conf.ke.namespace.foca.ItemBind;

/**
 * データ変換インターフェース.<br>
 * IntefaceAdapterに渡されたパラメータを出口メソッドのパラメータに変換します。<br>
 *
 * @param <IN> InterfaceAdapterの呼出元から渡されたパラメータ
 * @param <OUT> 出口メソッドに渡されるデータのインスタンス(生成済)
 */
public interface ParamTypeConverter<IN, OUT> {

    /**
     * データ変換処理.
     * @param input InterfaceAdapterの呼出元から渡されたパラメータ
     * @param output 出口メソッドに渡される予定のインスタンス(生成済)
     * @param cnvDef データ変換定義(JavaBean)
     * @return 実際に出口メソッドに渡すインスタンス
     * @throws Exception
     */
    OUT convert(IN input, OUT output, ItemBind cnvDef) throws Exception;
}
