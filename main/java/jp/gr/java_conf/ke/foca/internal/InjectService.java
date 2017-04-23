package jp.gr.java_conf.ke.foca.internal;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.List;

import jp.gr.java_conf.ke.foca.DIContents;
import jp.gr.java_conf.ke.foca.FocaException;
import jp.gr.java_conf.ke.foca.XmlConsistencyException;
import jp.gr.java_conf.ke.foca.annotation.Controller;
import jp.gr.java_conf.ke.foca.annotation.EntryPoint;
import jp.gr.java_conf.ke.foca.annotation.Gateway;
import jp.gr.java_conf.ke.foca.annotation.Log;
import jp.gr.java_conf.ke.foca.annotation.Presenter;
import jp.gr.java_conf.ke.foca.aop.MethodAdvice;
import jp.gr.java_conf.ke.foca.internal.injector.Injector;
import jp.gr.java_conf.ke.foca.internal.injector.InjectorFactory;
import jp.gr.java_conf.ke.namespace.foca.Joinpoint;
import jp.gr.java_conf.ke.util.Reflection;

/**
 * Created by YT on 2017/04/10.
 */

public class InjectService {

    public void execute(URL sourceURL, DIContents contents, Object target) throws FocaException {
        try {
            Class clazz = target.getClass();
            InjectorFactory factory = new InjectorFactory(contents);
            for (Field f : clazz.getDeclaredFields()) {
                for (Annotation anno : f.getDeclaredAnnotations()) {
                    startEntrypointService(f, anno, contents);
                    Injector injector = factory.create(anno);
                    injector.inject(target, f);
                }
            }
        } catch (FocaException e) {
            e.setURL(sourceURL);
            e.setDITarget(target);
            throw e;
        }
    }

    private void startEntrypointService(
            Field f, Annotation anno, DIContents contents) throws FocaException {
        String name = null;
        if (anno instanceof Controller) {
            name = ((Controller) anno).name();
        } else if (anno instanceof Presenter) {
            name = ((Presenter) anno).name();
        } else if (anno instanceof Gateway) {
            name = ((Gateway) anno).name();
        }
        if (name != null) {
            Joinpoint entrypoint = contents.selectEntrypoint(name);
            if (entrypoint != null) {
                final Object target = Reflection.newInstance(entrypoint.getInject().getClazz());
                Method entrypointMethod = null;
                int cnt = 0;
                for (Method m : target.getClass().getDeclaredMethods()) {
                    for (Annotation anno2 : m.getDeclaredAnnotations()) {
                        if (anno2 instanceof EntryPoint) {
                            entrypointMethod = m;
                            cnt++;
                        }
                    }
                }
                if (cnt == 0) return;
                if (1 < cnt) {
                    throw new XmlConsistencyException("重複マーカー:@EntryPoint");
                }
                for (Field f2 : target.getClass().getDeclaredFields()) {
                    for (Annotation anno3 : f2.getDeclaredAnnotations()) {
                        if (anno3 instanceof Log) {
                            InjectorFactory factory = new InjectorFactory(contents);
                            Injector injector = factory.create(anno3);
                            injector.inject(target, f2);
                        }
                    }
                }
                List<MethodAdvice> advices = MethodAdvices.asList(entrypoint, contents.aspecters());
                final AspectWeaver weaver = new AspectWeaver(target, advices);
                final Method method = entrypointMethod;
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            weaver.invoke(target, method, null);
                        } catch (Throwable th) {
                            th.printStackTrace();
                        }
                    }
                });
                thread.start();
            }
        }
    }

}
