package jp.gr.java_conf.ke.foca.converter;

/**
 * データ変換インターフェースから通知された例外.
 */
public class ParameterConvertException extends Exception {

    public ParameterConvertException(String msg, Exception e) {
        super(msg, e);
    }

}
