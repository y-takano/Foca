package jp.gr.java_conf.ke.foca.internal.util;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import jp.gr.java_conf.ke.foca.aop.MethodAdvice;
import jp.gr.java_conf.ke.foca.aop.SuppressThrow;

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

    public Object invoke(Method method, Object arg) throws Throwable {
        return invoke(null, method, arg == null ? null : new Object[]{arg});
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object arg = args == null ? null : args[0];

        Object realArg = arg;
        for (MethodAdvice advice : interceptors) {
            realArg = advice.beforeInvoke(arg, target, method);
        }

        Throwable th = null;
        Object ret = null;
        try {
            if (realArg == null) {
                ret = method.invoke(target);
            } else {
                ret = method.invoke(target, realArg);
            }
        } catch (InvocationTargetException e) {
            th = e.getTargetException();
        }

        List<SuppressThrow> supress = new LinkedList<SuppressThrow>();
        for (MethodAdvice advice : interceptors) {
            try {
                ret = advice.afterInvoke(ret, th, target, method);
            } catch(SuppressThrow st) {
                supress.add(st);
            }
        }

        for (SuppressThrow cause : supress) {
            for (MethodAdvice advice : interceptors) {
                try {
                    advice.handleSupressThrow(cause);
                } catch(Throwable t) {
                    t.printStackTrace();
                }
            }
        }

        if (th != null) {
            throw th;
        }
        return ret;
    }
}
