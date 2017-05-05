package jp.gr.java_conf.ke.foca;

import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.URL;
import java.util.Iterator;

/**
 * Created by YT on 2017/03/27.
 */

public abstract class FocaException extends Exception implements Iterable<FocaException>, Iterator<FocaException> {

    public enum ErrorCode {
        GTWY_ENTRY001("GE0001"),
        GTWY_INJECT001("GI0001"),
        CTRL_ENTRY001("CE0001"),
        CTRL_INJECT001("CI0001"),
        PRST_ENTRY001("PE0001"),
        PRST_INJECT001("PI0001"),
        ADPT_INJECT001("AI0001"),
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

    public URL getURL() {
        return url;
    }

    public void setURL(URL url) {
        this.url = url;
    }

    public Object getDITarget() {
        return diTarget;
    }

    public void setDITarget(Object target) {
        this.diTarget = target;
    }

    public FocaException getNext() {
        return next;
    }

    public void setNext(FocaException next) {
        this.next = next;
    }

    public ErrorCode getErrorCode() {
        return this.errCode;
    }

    public void setErrorCode(ErrorCode errCode) {
        this.errCode = errCode;
    }

    @Override
    public Iterator<FocaException> iterator() {
        return this;
    }

    @Override
    public boolean hasNext() {
        return next != null;
    }

    @Override
    public FocaException next() {
        FocaException n = getNext();
        setNext(n.getNext());
        return n;
    }

    public void printStackTrace() {
        printStackTrace(System.err);
    }

    public void printStackTrace(PrintStream ps) {
        printStackTraceInner(ps);
    }

    public void printStackTrace(PrintWriter pw) {
        printStackTraceInner(pw);
    }

    private void printStackTraceInner(Object writer) {
        if (writer == null) return;
        StringBuilder sb = new StringBuilder("<< DI container info >>");
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
