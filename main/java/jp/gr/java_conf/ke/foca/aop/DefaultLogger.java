package jp.gr.java_conf.ke.foca.aop;

import java.text.SimpleDateFormat;
import java.util.Date;

import jp.gr.java_conf.ke.foca.Foca;
import jp.gr.java_conf.ke.foca.annotation.Logger;

/**
 * Focaデフォルトロガーの実装.<br>
 * <br>
 * 標準出力にタイムスタンプ、ロガー運用レベル、ログ文字列を出力します.<br>
 * DIコンテナにロガーの設定が存在しない場合、本実装が適用されます。<br>
 *
 * @see Foca#getFocaDefaultLogger()
 * @see Foca#getDefaultLogger()
 */
public class DefaultLogger implements Logger {

    public Level getLevel(){return null;}

    public void debug(CharSequence str) {
        debug(null, str);
    }

    public void debug(Object obj) {
        debug(null, String.valueOf(obj));
    }

    public void debug(Throwable e, CharSequence str) {
        if (str != null) System.out.println(createMessage("[DEBUG]", str));
        if (e != null) e.printStackTrace(System.out);
    }

    public void debug(Throwable err, Object obj) {
        debug(err, String.valueOf(obj));
    }

    public void trace(CharSequence str) {
        if (str != null) System.out.println(createMessage("[TRACE]", str));
    }

    public void trace(Object obj) {
        trace(String.valueOf(obj));
    }

    public void info(CharSequence str) {
        if (str != null) System.out.println(createMessage("[INFO ]", str));
    }

    public void info(Object obj) {
        info(String.valueOf(obj));
    }

    public void error(Throwable e, CharSequence str) {
        if (str != null) System.err.println(createMessage("[ERROR]", str));
        if (e != null) System.err.println("exception class=" + e.getClass().getCanonicalName() + ", message=" + e.getLocalizedMessage());
    }

    public void error(Throwable err, Object obj) {
        error(err, String.valueOf(obj));
    }

    private String createMessage(String label, CharSequence msg) {
        return new StringBuilder(new SimpleDateFormat("yyyy/MM/dd HH:mm:ss:SSS z").format(new Date()))
                .append(": ").append(label).append(": ").append(msg).toString();
    }
}
