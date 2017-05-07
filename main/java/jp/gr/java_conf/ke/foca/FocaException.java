package jp.gr.java_conf.ke.foca;

import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.URL;
import java.util.Iterator;

/**
 * フレームワークの処理で発生する全ての例外の抽象クラス.<br>
 * <br>
 * 発生の原因となったDIコンテナ情報の取得メソッドを持ちます。<br>
 * また、スタックトレース出力時にDIコンテナ情報を自動的に出力します。<br>
 * <br>
 * 本例外クラスはDI処理中に発生した例外をリスト構造で表現するため、<br>
 * スタックトレース出力時に複数の例外メッセージが出力されます。<br>
 * <br>
 * <pre>
 * 使用例: 発生したすべてのエラーコードを標準出力に表示する
 *
 *     try {
 *         Foca.getDefault().inject(SomeObj.class);
 *
 *     } catch (FocaException e) {
 *
 *         // 自身のエラーコードを出力
 *         System.out.println(e.getErrorCode());
 *
 *         // 続きのエラーコードを出力
 *         for (FocaException next : e) {
 *             System.out.println(next.getErrorCode());
 *         }
 *     }
 *
 * </pre>
 */
public abstract class FocaException extends Exception implements Iterable<FocaException>, Iterator<FocaException> {

    /**
     * 発生元エラーコード.<br>
     * 発生時の処理を特定するためのコード.
     */
    public enum ErrorCode {
        /** GatewayのEntryPoint起動時 */
        GTWY_ENTRY001("GE0001"),
        /** Gatewayのフィールドインジェクション処理時 */
        GTWY_INJECT001("GI0001"),
        /** ControllerのEntryPoint起動時 */
        CTRL_ENTRY001("CE0001"),
        /** Controllerのフィールドインジェクション処理時 */
        CTRL_INJECT001("CI0001"),
        /** PresenterのEntryPoint起動時 */
        PRST_ENTRY001("PE0001"),
        /** Presenterのフィールドインジェクション処理時 */
        PRST_INJECT001("PI0001"),
        /** カスタムAdapter(BindDef不使用)のフィールドインジェクション処理時 */
        ADPT_INJECT001("AI0001"),
        /** Loggerのフィールドインジェクション処理時 */
        LOGR_INJECT001("LI0001"),
        ;
        private final String code;
        ErrorCode(String code) {
            this.code = code;
        }
        public String toString() {
            return "@" + code;
        }
    }

    private static final String NL = System.getProperty("line.separator");

    private ErrorCode errCode;
    private FocaException next;
    private URL url;
    private Object diTarget;

    protected FocaException(Throwable e) {
        super(e);
    }

    protected FocaException(String msg) {
        super(msg);
    }

    protected FocaException(String msg, Throwable e) {
        super(msg, e);
    }

    /**
     * DI情報: XMLファイルのURLを取得します.
     * @return url, JavaBeanによる設定時はnull
     */
    public URL getURL() {
        return url;
    }

    /**
     * DI情報: XMLファイルのURLを設定します.
     * @param url url
     */
    public void setURL(URL url) {
        this.url = url;
    }

    /**
     * DI情報: DI処理の対象オブジェクトを取得します.
     * @return DI対象オブジェクト
     */
    public Object getDITarget() {
        return diTarget;
    }

    /**
     * DI情報: DI処理の対象オブジェクトを設定します.
     * @param target DI対象オブジェクト
     */
    public void setDITarget(Object target) {
        this.diTarget = target;
    }

    /**
     * エラー情報: 後続例外を設定します.
     * @param next 後続例外
     */
    public void setNext(FocaException next) {
        this.next = next;
    }

    private FocaException getNext() {
        return next;
    }

    /**
     * エラー情報: 後続例外を取得します.
     * @return 後続例外, 後続なし時null
     */
    public FocaException next() {
        FocaException n = getNext();
        setNext(n.getNext());
        return n;
    }

    /**
     * エラー情報: エラーコードを取得します.
     * @return エラーコード
     * @see ErrorCode
     */
    public ErrorCode getErrorCode() {
        return this.errCode;
    }

    /**
     * エラー情報: エラーコードを取得します.
     * @param errCode エラーコード
     * @see ErrorCode
     */
    public void setErrorCode(ErrorCode errCode) {
        this.errCode = errCode;
    }

    /**
     * エラー情報: 後続例外のイテレータを取得します.
     * @return 後続例外のイテレータ, nonNull
     */
    public Iterator<FocaException> iterator() {
        return this;
    }

    /**
     * エラー情報: 後続例外の有無を取得します.
     * @return 後続あり時true
     */
    public boolean hasNext() {
        return next != null;
    }

    /**
     * エラー情報: 後続例外のリスト構造を削除します.
     */
	public void remove() {
		next = null;
	}

    public void printStackTrace() {
        printStackTrace(System.err);
    }

    public void printStackTrace(PrintStream ps) {
        printStackTraceCore(ps);
    }

    public void printStackTrace(PrintWriter pw) {
        printStackTraceCore(pw);
    }

    private void printStackTraceCore(Object writer) {
        if (writer == null) return;
        StringBuilder sb = new StringBuilder("<< DI info >>");
        if (url != null) {
            sb.append(NL);
            sb.append("Source: ");
            sb.append(getURL());
        }
        if (diTarget != null) {
            sb.append(NL);
            sb.append("Target: ");
            sb.append(getDITarget().getClass().getCanonicalName());
        }
        int cnt = 0;
        sb.append(NL);
        sb.append(NL);
        sb.append("<< exception list >>");
        sb.append(NL);
        sb.append("[");
        sb.append(cnt);
        sb.append("]: ");
        ErrorCode ec = getErrorCode();
        if (ec != null) {
            sb.append(ec);
            sb.append(": ");
        }
        sb.append(getMessage());
        if (hasNext()) {
            for (FocaException exp : this) {
                cnt++;
                sb.append("[");
                sb.append(cnt);
                sb.append("]: ");
                ec = exp.getErrorCode();
                if (ec != null) {
                    sb.append(ec);
                    sb.append(": ");
                }
                sb.append(exp.getMessage());
                sb.append(NL);
            }
        }
        sb.append(NL);
        sb.append("<< stack trace >>");
        sb.append(NL);
        if (writer instanceof PrintStream) {
            PrintStream ps = (PrintStream)writer;
            ps.print(sb.toString());
            super.printStackTrace(ps);
        } else if (writer instanceof PrintWriter) {
            PrintWriter pw = (PrintWriter)writer;
            pw.print(sb.toString());
            super.printStackTrace(pw);
        }
    }

}
