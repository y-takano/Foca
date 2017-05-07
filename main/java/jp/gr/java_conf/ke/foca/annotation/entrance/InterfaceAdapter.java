package jp.gr.java_conf.ke.foca.annotation.entrance;

/**
 *
 *
 *
 * @param <P> パラメータクラス
 * @param <E> 例外クラス
 * @see Controller
 * @see Presenter
 * @see Gateway
 */
public interface InterfaceAdapter<P, E extends Throwable> {

    /**
     * 戻り値なしのインターフェース呼び出し.<br>
     * @param param 型指定されたパラメータ
     * @throws E 型指定された例外
     */
    void invoke(P param) throws E;
}
