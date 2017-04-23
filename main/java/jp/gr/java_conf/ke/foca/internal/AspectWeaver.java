package jp.gr.java_conf.ke.foca.internal;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.List;

import jp.gr.java_conf.ke.foca.AdviceRuntimeException;
import jp.gr.java_conf.ke.foca.aop.MethodAdvice;

/**
 * Created by YT on 2017/04/20.
 */
public class AspectWeaver implements InvocationHandler {

    private Object target;
    private List<MethodAdvice> interceptors;

    public AspectWeaver(Object target, List<MethodAdvice> interceptors) {
        this.target = target;
        this.interceptors = interceptors == null ? Collections.<MethodAdvice>emptyList() : interceptors;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object arg = args == null ? null : args[0];
        Throwable th = null;
        try {
            for (MethodAdvice advice : interceptors) {
                th = advice.beforeInvoke(arg, target, method);
            }
        } catch (Throwable e) {
            AdviceRuntimeException rte = new AdviceRuntimeException("実行前インターセプタで例外が発生しました。");
            rte.setTargetException(e);
            throw rte;
        }

        if (th != null) {
            throw th;
        }

        Object ret = null;
        try {
            if (arg == null) {
                ret = method.invoke(target);
            } else {
                ret = method.invoke(target, arg);
            }
        } catch (InvocationTargetException e) {
            th = e.getTargetException();
        } catch (Throwable t) {
            th = t;
        }

        try {
            for (MethodAdvice advice : interceptors) {
                th = advice.afterInvoke(ret, th, target, method);
            }
        } catch (Throwable e) {
            AdviceRuntimeException rte = new AdviceRuntimeException("実行後インターセプタで例外が発生しました。");
            rte.setTargetException(e);
            throw rte;
        }

        if (th != null) {
            throw th;
        }
        return ret;
    }
}
