package jp.gr.java_conf.ke.foca.aop;

import java.lang.reflect.Method;

import jp.gr.java_conf.ke.foca.Foca;
import jp.gr.java_conf.ke.foca.annotation.Logger;

/**
 * メソッド呼び出し前後でトレースログを出力するアドバイス.<br>
 * <br>
 * 使用するトレースログは現在のDIコンテナに設定されたデフォルトロガーです。<br>
 * ロガー取得に使用するメソッド: {@link Foca#getDefaultLogger()}
 *
 * @see Foca
 */
public class TraceLogAdvice implements MethodAdvice {

    public Object beforeInvoke(Object arg, Object target, Method method) {
        Logger log = Foca.getDefault().getDefaultLogger();
        StringBuilder sb = new StringBuilder();
        sb.append("call  : ");
        sb.append(target.getClass().getCanonicalName());
        sb.append("#");
        sb.append(method.getName());
        Class<?>[] classes = method.getParameterTypes();
        if (classes != null) {
            sb.append("(");
            for (int i=0; i<classes.length; i++) {
                if (0 < i) sb.append(",");
                sb.append(classes[i].getCanonicalName());
            }
            sb.append(")");
        } else {
            sb.append("null");
        }
        sb.append(", param=");
        if (arg != null) {
            sb.append(String.valueOf(arg));
        } else {
            sb.append("null");
        }
        log.trace(sb.toString());
        return arg;
    }

    public Object afterInvoke(Object ret, Throwable th, Object target, Method method) {
        Logger log = Foca.getDefault().getDefaultLogger();
        StringBuilder sb = new StringBuilder();
        if (th == null) {
            sb.append("return: ");
            sb.append(target.getClass().getCanonicalName());
            sb.append("#");
            sb.append(method.getName());
            Class<?>[] classes = method.getParameterTypes();
            if (classes != null) {
                sb.append("(");
                for (int i=0; i<classes.length; i++) {
                    if (0 < i) sb.append(",");
                    sb.append(classes[i].getCanonicalName());
                }
                sb.append(")");
            } else {
                sb.append("null");
            }
            sb.append(", return=(");
            sb.append(method.getReturnType().getCanonicalName());
            sb.append(") ");
            sb.append(String.valueOf(ret));
            log.trace(sb.toString());
        } else {
            sb.append("exception occurred in: ");
            sb.append(method.getDeclaringClass().getCanonicalName());
            sb.append("#");
            sb.append(method.getName());
            log.trace(sb.toString());
            log.error(th, sb.toString());
        }
        return ret;
    }

    public void handleSupressThrow(SuppressThrow cause) {

    }

}
