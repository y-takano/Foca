package jp.gr.java_conf.ke.foca.aop;

import java.lang.reflect.Method;

/**
 * Created by YT on 2017/04/14.
 */
public interface MethodAdvice<P, R, E1 extends Throwable, E2 extends Throwable> {

    E1 beforeInvoke(P arg, Object target, Method method);

    E2 afterInvoke(R ret, Throwable th, Object target, Method method);
}
