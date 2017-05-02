package jp.gr.java_conf.ke.foca.internal.validator;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import jp.gr.java_conf.ke.foca.FocaException;
import jp.gr.java_conf.ke.foca.internal.InjectionOrder;

/**
 * Created by YT on 2017/04/27.
 */

public abstract class Validator {

    private final InjectionOrder[] orders;

    Validator(InjectionOrder[] orders) {
        if (orders == null) this.orders = new InjectionOrder[0];
        else this.orders = orders;
    }

    public final List<EnabledMarker> validate() throws FocaException {
        return validateOverrap(filterOrder(createList()));
    }

    protected InjectionOrder[] orders() {
        return orders;
    }

    protected abstract List<EnabledMarker> createList();

    protected abstract List<EnabledMarker> validateOverrap(
            List<EnabledMarker> let) throws FocaException;

    protected List<EnabledMarker> filterOrder(List<EnabledMarker> let) {
        List<EnabledMarker> ret = new ArrayList<EnabledMarker>();
        for (EnabledMarker current : let) {
            Annotation anno = current.annotation();
            for (InjectionOrder order : orders()) {
                if (order.isRelative(anno)) {
                    ret.add(current);
                }
            }
        }
        return ret;
    }

    public static class EnabledMarker {
        private final Field field;
        private final Method method;
        private final Annotation anno;

        EnabledMarker(Field field, Annotation anno) {
            this.field = field;
            this.method = null;
            this.anno = anno;
        }

        EnabledMarker(Method method, Annotation anno) {
            this.field = null;
            this.method = method;
            this.anno = anno;
        }

        public Field field() {
            return field;
        }

        public Method method() {
            return method;
        }

        public Annotation annotation() {
            return anno;
        }
    }
}
