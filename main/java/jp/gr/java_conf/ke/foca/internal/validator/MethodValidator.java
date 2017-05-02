package jp.gr.java_conf.ke.foca.internal.validator;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import jp.gr.java_conf.ke.foca.FocaException;
import jp.gr.java_conf.ke.foca.XmlConsistencyException;
import jp.gr.java_conf.ke.foca.internal.InjectionOrder;

/**
 * Created by YT on 2017/04/27.
 */

class MethodValidator extends Validator {

    private Method[] methods;

    MethodValidator(Method[] methods, InjectionOrder[] orders) {
        super(orders);
        this.methods = methods;
    }

    private static final Class[] enable = new Class[] {
            InjectionOrder.Inputport.getRelativeClass(),
            InjectionOrder.Driver.getRelativeClass(),
            InjectionOrder.View.getRelativeClass(),
    };

    private boolean isEnable(Annotation anno) {
        for (Class c : enable) {
            if (c.getCanonicalName().equals(anno.annotationType().getCanonicalName())) {
                return true;
            }
        }
        return false;
    }

    protected List<EnabledMarker> createList() {
        List<EnabledMarker> ret = new ArrayList<EnabledMarker>();
        for (Method m : methods) {
            for (Annotation anno : m.getDeclaredAnnotations()) {
                if (isEnable(anno)) {
                    EnabledMarker marker = new EnabledMarker(m, anno);
                    ret.add(marker);
                }
            }
        }
        return ret;
    }

    protected List<EnabledMarker> validateOverrap(List<EnabledMarker> let) throws FocaException {
        List<Annotation> annoList = new ArrayList<Annotation>();
        List<Method> methodList = new ArrayList<Method>();
        for (EnabledMarker current : let) {
            annoList.add(current.annotation());
            methodList.add(current.method());
        }

        if (annoList.size() < 2) {
            // NOP
        } else {
            String message = "{";
            for (int i=0; i<annoList.size(); i++) {
                Annotation anno = annoList.get(i);
                message += " @";
                message += anno.annotationType().getName();
                Method m = methodList.get(i);
                message += " ";
                message += m.getName();
            }
            message += "}";
            throw new XmlConsistencyException(
                    "overlap annotation:" + message);
        }

        if (methodList.size() < 2) {
            // NOP
        } else {
            throw new InternalError();
        }
        return let;
    }
}
