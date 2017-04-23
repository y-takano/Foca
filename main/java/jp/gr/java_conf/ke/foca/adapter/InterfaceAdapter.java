package jp.gr.java_conf.ke.foca.adapter;

/**
 * Created by YT on 2017/03/26.
 */

public interface InterfaceAdapter<P, E extends Throwable> {

    void invoke(P param) throws E;
}
