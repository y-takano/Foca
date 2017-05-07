package jp.gr.java_conf.ke.foca.aop;

/**
 * MethodAdviceの判断により呼出先で発生した例外を抑止することをフレームワークに通知する.
 */
public class SuppressThrow extends Exception {

    private final int code;
    private final Throwable target;

    /**
     * 例外抑止を通知する例外オブジェクトを生成する.
     * @param code エラーコード
     * @param message エラーメッセージ
     * @param target 呼出先で発生した例外
     */
    public SuppressThrow(int code, String message, Throwable target) {
        super(message);
        this.code = code;
        this.target = target;
    }

    /**
     * エラーコードを取得します.
     * @return エラーコード.
     */
    public int getCode() {
        return code;
    }

    /**
     * 呼出先で発生した例外を取得します.
     * @return 呼出先で発生した例外
     */
    public Throwable getTargetException() {
        return target;
    }
}
