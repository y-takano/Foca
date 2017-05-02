package jp.gr.java_conf.ke.foca.internal;

import java.lang.annotation.Annotation;

import jp.gr.java_conf.ke.foca.annotation.Controller;
import jp.gr.java_conf.ke.foca.annotation.Driver;
import jp.gr.java_conf.ke.foca.annotation.EntryPoint;
import jp.gr.java_conf.ke.foca.annotation.Gateway;
import jp.gr.java_conf.ke.foca.annotation.InputPort;
import jp.gr.java_conf.ke.foca.annotation.Log;
import jp.gr.java_conf.ke.foca.annotation.Presenter;
import jp.gr.java_conf.ke.foca.annotation.View;

/**
 * Created by YT on 2017/05/02.
 */
public enum InjectionOrder {
    Entrypoint(EntryPoint.class),
    Controller(Controller.class),
    Gateway(Gateway.class),
    Inputport(InputPort.class),
    Presenter(Presenter.class),
    View(View.class),
    Driver(Driver.class),
    Log(Log.class),;
    private final Class<?> annotationClass;

    InjectionOrder(Class<?> annotationClass) {
        this.annotationClass = annotationClass;
    }

    public boolean isRelative(Annotation annotation) {
        return annotationClass.isInstance(annotation);
    }

    public Class<?> getRelativeClass() {
        return annotationClass;
    }

    public static InjectionOrder valueOf(Annotation anno) {
        for (InjectionOrder order : values()) {
            if (order.isRelative(anno)) return order;
        }
        return null;
    }

    public static InjectionOrder valueOf(Class<? extends Annotation> annoClass) {
        for (InjectionOrder order : values()) {
            if (order.annotationClass.equals(annoClass)) return order;
        }
        return null;
    }
}
