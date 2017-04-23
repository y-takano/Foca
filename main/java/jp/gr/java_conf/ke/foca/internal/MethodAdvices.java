package jp.gr.java_conf.ke.foca.internal;

import java.util.LinkedList;
import java.util.List;

import jp.gr.java_conf.ke.foca.FocaException;
import jp.gr.java_conf.ke.foca.aop.MethodAdvice;
import jp.gr.java_conf.ke.namespace.foca.Aspect;
import jp.gr.java_conf.ke.namespace.foca.Joinpoint;
import jp.gr.java_conf.ke.namespace.foca.PointcutType;
import jp.gr.java_conf.ke.util.Reflection;

/**
 * Created by YT on 2017/04/24.
 */

public class MethodAdvices {

    public static List<MethodAdvice> asList(
            Joinpoint joinpoint, Iterable<Aspect> aspectList) throws FocaException {

        PointcutType pointcutType = joinpoint.getPointcut();
        List<MethodAdvice> advices;
        switch (pointcutType) {
            case IGNORE:
                advices = null;
                break;
            case WHITE_LIST:
                advices = whiteList(aspectList, joinpoint.getList());
                break;
            case BLACK_LIST:
                advices = blackList(aspectList, joinpoint.getList());
                break;
            case ALL:
                advices = allList(aspectList);
                break;
            default:
                throw new InternalError();
        }
        return advices;
    }

    private static List<MethodAdvice> blackList(
            Iterable<Aspect> aspectList, Iterable<String> removeList) throws FocaException {
        List<MethodAdvice> ret = allList(aspectList);
        if (removeList != null) {
            LinkedList<String> remove1 = new LinkedList<String>();
            for (String name : removeList) {
                for (Aspect a : aspectList) {
                    if (a.getName().equals(name)) {
                        remove1.add(a.getAdvice());
                    }
                }
            }
            LinkedList<MethodAdvice> remove2 = new LinkedList<MethodAdvice>();
            for (String className : remove1) {
                for (MethodAdvice advice : ret) {
                    if (className.equals(advice.getClass().getCanonicalName())) {
                        remove2.add(advice);
                    }
                }
            }
            for (MethodAdvice advice : remove2) {
                ret.remove(advice);
            }
        }
        return ret;
    }

    private static List<MethodAdvice> whiteList(
            Iterable<Aspect> aspectList,Iterable<String> addList)
            throws FocaException {
        List<MethodAdvice> ret = new LinkedList<MethodAdvice>();
        if (addList != null) {
            for (Aspect a : aspectList) {
                for (String name : addList) {
                    if (a.getName().equals(name)) {
                        ret.add(Reflection.newInstance(a.getAdvice(), MethodAdvice.class));
                    }
                }
            }
        }
        return ret;
    }

    private static List<MethodAdvice> allList(Iterable<Aspect> aspectList) throws FocaException {
        List<MethodAdvice> ret = new LinkedList<MethodAdvice>();
        for (Aspect a : aspectList) {
            ret.add(Reflection.newInstance(a.getAdvice(), MethodAdvice.class));
        }
        return ret;
    }
}
