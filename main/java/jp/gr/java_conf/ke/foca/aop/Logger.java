package jp.gr.java_conf.ke.foca.aop;

/**
 * Created by YT on 2017/04/15.
 */

public interface Logger {

    enum Level {
        DEBUG,
        TRACE,
        INFO,
        ERROR,
    }

    Level getLevel();

    void debug(CharSequence str);

    void debug(Object obj);

    void debug(Throwable err, CharSequence str);

    void debug(Throwable err, Object obj);

    void trace(CharSequence str);

    void trace(Object obj);

    void info(CharSequence str);

    void info(Object obj);

    void error(Throwable err, CharSequence str);

    void error(Throwable err, Object obj);
}
