package jp.gr.java_conf.ke.foca.aop;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by YT on 2017/04/16.
 */

public class DefaultLogger implements Logger {

    @Override
    public Level getLevel(){return null;}

    @Override
    public void debug(CharSequence str) {
        debug(null, str);
    }

    @Override
    public void debug(Object obj) {
        debug(null, String.valueOf(obj));
    }

    @Override
    public void debug(Throwable e, CharSequence str) {
        if (str != null) System.out.println(createMessage("[DEBUG]", str));
        if (e != null) e.printStackTrace(System.out);
    }

    @Override
    public void debug(Throwable err, Object obj) {
        debug(err, String.valueOf(obj));
    }

    @Override
    public void trace(CharSequence str) {
        if (str != null) System.out.println(createMessage("[TRACE]", str));
    }

    @Override
    public void trace(Object obj) {
        trace(String.valueOf(obj));
    }

    @Override
    public void info(CharSequence str) {
        if (str != null) System.out.println(createMessage("[INFO ]", str));
    }

    @Override
    public void info(Object obj) {
        info(String.valueOf(obj));
    }

    @Override
    public void error(Throwable e, CharSequence str) {
        if (str != null) System.err.println(createMessage("[ERROR]", str));
        if (e != null) System.err.println("exception class=" + e.getClass().getCanonicalName() + ", message=" + e.getLocalizedMessage());
    }

    @Override
    public void error(Throwable err, Object obj) {
        error(err, String.valueOf(obj));
    }

    private String createMessage(String label, CharSequence msg) {
        return new StringBuilder(new SimpleDateFormat("yyyy/MM/dd HH:mm:ss:SSS z").format(new Date()))
                .append(": ").append(label).append(": ").append(msg).toString();
    }
}
