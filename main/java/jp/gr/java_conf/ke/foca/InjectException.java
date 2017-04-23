package jp.gr.java_conf.ke.foca;

/**
 * Created by YT on 2017/03/26.
 */

public class InjectException extends FocaException {

    public InjectException(String msg) {
        super(msg);
    }

    public InjectException(String msg, Exception e) {
        super(msg, e);
    }

}
