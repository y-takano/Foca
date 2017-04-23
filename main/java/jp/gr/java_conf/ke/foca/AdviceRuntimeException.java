package jp.gr.java_conf.ke.foca;

/**
 * Created by YT on 2017/04/22.
 */

public class AdviceRuntimeException extends RuntimeException {

    private Throwable targetException;

    public AdviceRuntimeException(String msg) {
        super(msg);
    }

    public void setTargetException(Throwable targetException) {
        this.targetException = targetException;
    }

    public Throwable getTargetException() {
        return targetException;
    }
}
