package jp.gr.java_conf.ke.foca.internal.injector;

import java.lang.reflect.Field;

import jp.gr.java_conf.ke.foca.DIContents;
import jp.gr.java_conf.ke.foca.Foca;
import jp.gr.java_conf.ke.foca.FocaException;
import jp.gr.java_conf.ke.foca.annotation.entrance.InterfaceAdapter;
import jp.gr.java_conf.ke.foca.annotation.Logger;
import jp.gr.java_conf.ke.util.Reflection;

/**
 * Created by YT on 2017/04/23.
 */

class LoggerInjector extends Injector {

    LoggerInjector(DIContents containts, String adapterName) {
        super(containts, adapterName, null);
    }

    @Override
    InterfaceAdapter<?, ?> getAdapter(DIContents containts, String adapterName) throws FocaException {
        return null;
    }

    public void inject(Object injectee, Field targetField) {
        jp.gr.java_conf.ke.namespace.foca.Logger logger = getContents().getLogger(getName()).rawValue();
        Foca fw = Foca.getDefault();
        Logger log;
        if (logger == null) {
            log = fw.getDefaultLogger();
        } else {
            log = fw.getLogger(logger.getName());
        }
        Reflection.setFieldValue(injectee, targetField, log);
    }
}
