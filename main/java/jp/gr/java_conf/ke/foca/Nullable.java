package jp.gr.java_conf.ke.foca;

/**
 * Nullの可能性がある値オブジェクト.<br>
 * @param <E> 値オブジェクトのクラス
 */
public class Nullable<E> {

    private final E value;
    private Runnable nullHandler = new Runnable() {
        public void run() {
            throw new NullPointerException();
        }
    };

    Nullable(E value) {
        this.value = value;
    }

    /**
     * 値オブジェクトをそのまま取得します.<br>
     * 値オブジェクトがnullの場合はnullを返します。
     * @return 値オブジェクト
     */
    public E rawValue() {
        return value;
    }

    /**
     * 値オブジェクトを取得します.<br>
     * 値オブジェクトがnullの場合、設定された処理を実施します。<br>
     * {@link #setNullHandler(Runnable)}未使用の場合、{@link NullPointerException}をスローします。<br>
     * @return 値オブジェクト
     * @see #setNullHandler(Runnable)
     */
    public E requireNonNull() {
        if (isNull()) nullHandler.run();
        return value;
    }

    /**
     * 値オブジェクトのnull判定を実施します.
     * @return 値オブジェクトがnullの場合true
     */
    public boolean isNull() {
        return value == null;
    }

    /**
     * 値オブジェクトがnullの場合の振る舞いを設定します.<br>
     * 本メソッド未使用の場合は、{@link NullPointerException}をスローするのみです。<br>
     * @param handler notNull
     */
    public void setNullHandler(Runnable handler) {
        if (handler == null) return;
        this.nullHandler = handler;
    }
}
