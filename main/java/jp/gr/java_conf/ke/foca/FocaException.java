package jp.gr.java_conf.ke.foca;

import java.lang.reflect.Method;
import java.net.URL;
import java.util.Iterator;

/**
 * Created by YT on 2017/03/27.
 */

public abstract class FocaException extends Exception implements Iterable<FocaException>, Iterator<FocaException> {

    private static final String NL = System.getProperty("line.separator");

    private FocaException next;
    private URL url;
    private Object diTarget;
    private Method invokeTarget;

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

    public Method getInvokeTarget() {
        return invokeTarget;
    }

    public void setInvokeTarget(Method target) {
        this.invokeTarget = target;
    }

    public FocaException getNext() {
        return next;
    }

    public void setNext(FocaException next) {
        this.next = next;
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
        return next;
    }

    public String getMessage() {
        StringBuilder sb = new StringBuilder();

        if (url != null) {
            sb.append("error occurred in");
            sb.append(NL);
            sb.append(NL);
            sb.append("DISource:");
            sb.append(NL);
            sb.append(url);
            sb.append(NL);
            if (diTarget != null) {
                sb.append("DITarget:");
                sb.append(NL);
                sb.append(diTarget.getClass().getCanonicalName());
                sb.append(NL);
            }
            if (invokeTarget != null) {
                sb.append("InvokeTarget:");
                sb.append(NL);
                sb.append(invokeTarget.getDeclaringClass().getCanonicalName());
                sb.append(invokeTarget.getName());
                sb.append(NL);
            }
        }
        sb.append(getStackTraceString());
        return sb.toString();
    }

    public void printStackTrace() {
        System.err.println(getStackTraceString());
    }

    private String getStackTraceString() {
        StringBuilder sb = new StringBuilder();
        int cnt = 0;
        sb.append(NL);
        sb.append("[");
        sb.append(cnt);
        sb.append("]:");
        sb.append(NL);
        sb.append(getRawMessage());
        if (hasNext()) {
            for (FocaException exp : this) {
                cnt++;
                sb.append(NL);
                sb.append(NL);
                sb.append("[");
                sb.append(cnt);
                sb.append("]:");
                sb.append(NL);
                sb.append(exp.getRawMessage());
            }
        }
        return sb.toString();
    }

    private String getRawMessage() {
        return super.getMessage();
    }

}
