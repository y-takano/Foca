package jp.gr.java_conf.ke.foca.aop;

import java.lang.reflect.Method;

/**
 * InterfaceAdapter呼び出し時のAdviceインターフェース.<br>
 * <br>
 * @param <P> パラメータ(引数)クラス
 * @param <R> リターン(戻り値)クラス
 * @param <E1> {@link #beforeInvoke}で発生する例外
 * @param <E2> {@link #afterInvoke}で発生する例外
 */
public interface MethodAdvice<P, R, E1 extends Throwable, E2 extends Throwable> {

    /**
     * メソッド呼出前の任意処理を実行します.<br>
     * <br>
     * @param arg 呼出先に渡す予定のパラメータ
     * @param target 呼出先オブジェクト
     * @param method 呼出先メソッド
     * @return 実際に呼出先へ渡すパラメータ
     * @throws E1 呼出元へ例外を通知する場合
     */
    P beforeInvoke(P arg, Object target, Method method) throws E1;

    /**
     * メソッド呼び出し後の任意処理を実行します.<br>
     * <br>
     * @param ret 呼出先から受取った戻り値
     * @param th 呼出先で発生した例外
     * @param target 呼出先オブジェクト
     * @param method 呼出先メソッド
     * @return 呼出元へ渡される戻り値
     * @throws E2 呼出元へ例外を通知する場合
     * @throws SuppressThrow 呼出先で発生した例外を抑止する場合
     */
    R afterInvoke(R ret, Throwable th, Object target, Method method) throws E2, SuppressThrow;

    /**
     * Joinpointで例外抑止が指示された場合、各Adviceに通知されます.<br>
     * @param cause 例外抑止情報
     */
    void handleSupressThrow(SuppressThrow cause);
}
