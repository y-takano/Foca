package jp.gr.java_conf.ke.foca.internal.validator;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import jp.gr.java_conf.ke.foca.annotation.Log;
import jp.gr.java_conf.ke.foca.internal.InjectionOrder;
import jp.gr.java_conf.ke.foca.annotation.Controller;
import jp.gr.java_conf.ke.foca.annotation.Gateway;
import jp.gr.java_conf.ke.foca.annotation.Presenter;

/**
 * Created by YT on 2017/04/27.
 */

public class Annotations {

    public static Validator collect(Field[] fields, InjectionOrder... order) {
        return new FieldValidator(fields, order);
    }

    public static Validator collect(Method[] methods, InjectionOrder... order) {
        return new MethodValidator(methods, order);
    }

    public static String getFlowName(Annotation anno) {
        String name = null;
        if (anno instanceof Controller) {
            name = ((Controller) anno).name();
        } else if (anno instanceof Presenter) {
            name = ((Presenter) anno).name();
        } else if (anno instanceof Gateway) {
            name = ((Gateway) anno).name();
        }
        return name;
    }

    public static String getLogName(Annotation anno) {
        String name = null;
        if (anno instanceof Log) {
            name = ((Log) anno).name();
        }
        return name;
    }
}
