package jp.gr.java_conf.ke.foca;

/**
 * DI処理の本処理実行時に発生した例外.
 */
public class InjectException extends FocaException {

    public InjectException(String msg) {
        super(msg);
    }

    public InjectException(Throwable e) {
        super(e);
    }

    public InjectException(String msg, Throwable e) {
        super(msg, e);
    }

}
