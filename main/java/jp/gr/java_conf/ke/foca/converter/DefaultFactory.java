package jp.gr.java_conf.ke.foca.converter;

import jp.gr.java_conf.ke.util.Reflection;

/**
 * データ変換対象オブジェクト生成インターフェース<br>
 * <br>
 * @param <DTO> データモデルの型クラス
 */
public class DefaultFactory<DTO> implements OutModelFactory<DTO> {

    /**
     * 指定された型パラメータのオブジェクトを生成します.
     * @param outClass 型パラメータのクラス名
     * @return 生成したインスタンス
     * @throws Exception
     */
    public DTO createParameter(String outClass) throws Exception {
        return Reflection.newInstance(outClass);
    }
}
