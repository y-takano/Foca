package jp.gr.java_conf.ke.foca;

/**
 * XMLまたはJavaBeanのDI設定情報とアプリケーションの実装が不整合.
 */
public class XmlConsistencyException extends FocaException {

    public XmlConsistencyException(String msg) {
        super(msg);
    }

    public XmlConsistencyException(String msg, Throwable e) {
        super(msg, e);
    }

}
