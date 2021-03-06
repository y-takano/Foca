package jp.gr.java_conf.ke.util;

/**
 * Created by YT on 2017/04/22.
 */

public class ReflectiveException extends RuntimeException {

    public ReflectiveException(String msg) {
        this(msg, null);
    }

    public ReflectiveException(Throwable cause) {
        super(cause);
    }

    public ReflectiveException(String msg, Throwable cause) {
        super(msg + "(" + cause.getClass().getSimpleName() + ")", cause);
    }
}
