package jp.gr.java_conf.ke.foca.aop;

import java.lang.reflect.Method;

import jp.gr.java_conf.ke.foca.Foca;
import jp.gr.java_conf.ke.foca.FocaException;

/**
 * Created by YT on 2017/04/20.
 */

public class TraceLogAdvice implements MethodAdvice {

    @Override
    public Throwable beforeInvoke(Object arg, Object target, Method method) {
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
        return null;
    }

    @Override
    public Throwable afterInvoke(Object ret, Throwable th, Object target, Method method) {
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
        return th;
    }

}
