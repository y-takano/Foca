package jp.gr.java_conf.ke.foca.internal;

/**
 * Created by YT on 2017/05/01.
 */

public class InjectTarget {

    private final Object target;

    public InjectTarget(Object target) {
        this.target = target;
    }

    public Object getInstance() {
        return target;
    }


}
