package jp.gr.java_conf.ke.foca.annotation.entrance;

/**
 * Created by YT on 2017/05/05.
 */

public interface FetchableAdapter<P, R, E extends Throwable> extends InterfaceAdapter<P, E> {

    R fetch(P param) throws E;
}
