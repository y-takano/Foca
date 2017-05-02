package jp.gr.java_conf.ke.foca.internal.validator;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import jp.gr.java_conf.ke.foca.FocaException;
import jp.gr.java_conf.ke.foca.XmlConsistencyException;
import jp.gr.java_conf.ke.foca.internal.InjectionOrder;

/**
 * Created by YT on 2017/04/27.
 */

class FieldValidator extends Validator {

    private Field[] fields;

    FieldValidator(Field[] fields, InjectionOrder[] orders) {
        super(orders);
        this.fields = fields;
    }

    private static final Class[] enable = new Class[] {
            InjectionOrder.Controller.getRelativeClass(),
            InjectionOrder.Gateway.getRelativeClass(),
            InjectionOrder.Inputport.getRelativeClass(),
            InjectionOrder.View.getRelativeClass(),
            InjectionOrder.Driver.getRelativeClass(),
            InjectionOrder.Presenter.getRelativeClass(),
            InjectionOrder.Log.getRelativeClass(),
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
        for (Field f : fields) {
            for (Annotation anno : f.getDeclaredAnnotations()) {
                if (isEnable(anno)) {
                    EnabledMarker marker = new EnabledMarker(f, anno);
                    ret.add(marker);
                }
            }
        }
        return ret;
    }

    protected List<EnabledMarker> validateOverrap(List<EnabledMarker> let) throws FocaException {
        List<Annotation> annoList = new ArrayList<Annotation>();
        List<Field> fieldList = new ArrayList<Field>();
        for (EnabledMarker current : let) {
            annoList.add(current.annotation());
            fieldList.add(current.field());
        }

        if (annoList.size() < 2) {
            // NOP
        } else if (annoList.size() == 2) {
            Annotation anno = annoList.get(0);
            if (!InjectionOrder.Gateway.isRelative(anno)) {
                throw new XmlConsistencyException(
                        "disable annotation: " + anno);
            }
            anno = annoList.get(1);
            if (!InjectionOrder.Gateway.isRelative(anno)) {
                throw new XmlConsistencyException(
                        "disable annotation: " + anno);
            }
        } else {
            String message = "{";
            for (int i=0; i<annoList.size(); i++) {
                Annotation anno = annoList.get(i);
                message += " @";
                message += anno.annotationType().getName();
                Field f = fieldList.get(i);
                message += " ";
                message += f.getType().getName();
                message += " ";
                message += f.getName();
            }
            message += "}";
            throw new XmlConsistencyException(
                    "overlap annotation:" + message);
        }

        if (fieldList.size() < 2) {
            // NOP
        } else if (fieldList.size() == 2) {
            if (fieldList.get(0).equals(fieldList.get(1))) {
                throw new XmlConsistencyException(
                        "overlap annotation: "
                                + fieldList.get(0));
            }
        } else {
            throw new InternalError();
        }
        return let;
    }

}
