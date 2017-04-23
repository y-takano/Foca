package jp.gr.java_conf.ke.foca.internal.adapter;

import java.lang.annotation.Annotation;
import java.util.List;

import jp.gr.java_conf.ke.foca.Foca;
import jp.gr.java_conf.ke.foca.XmlConsistencyException;
import jp.gr.java_conf.ke.foca.adapter.OutModelFactory;
import jp.gr.java_conf.ke.foca.aop.MethodAdvice;
import jp.gr.java_conf.ke.foca.FocaException;
import jp.gr.java_conf.ke.foca.adapter.InterfaceAdapter;
import jp.gr.java_conf.ke.foca.internal.MethodAdvices;
import jp.gr.java_conf.ke.util.Reflection;
import jp.gr.java_conf.ke.namespace.foca.Aspect;
import jp.gr.java_conf.ke.namespace.foca.BindDef;
import jp.gr.java_conf.ke.namespace.foca.Converter;
import jp.gr.java_conf.ke.namespace.foca.Joinpoint;

/**
 * Created by YT on 2017/04/09.
 */

public class AdapterFactory {

    public InterfaceAdapter<?, ?> createAdapter(
            Converter cnv, Class<? extends Annotation> annoClass, Joinpoint joinpoint, Iterable<Aspect> aspectList)
            throws FocaException {

        InterfaceAdapter ret;
        List<MethodAdvice> advices = MethodAdvices.asList(joinpoint, aspectList);
        String injectClassName = joinpoint.getInject().getClazz();
        BindDef def = cnv.getBindDef();
        if (def == null) {
            String className = cnv.getInject().getClazz();
            InterfaceAdapter plugin = Reflection.newInstance(className, InterfaceAdapter.class);
            Object field = Reflection.newInstance(injectClassName);
            Foca.getDefault().inject(field);
            ret = new PluginAdapter(plugin, annoClass, field, advices);
        } else {
            String factoryName = def.getFactory();
            String outModelName = def.getOutModel();
            Object outModel = createModelFactory(factoryName, outModelName);
            Object callee = Reflection.newInstance(injectClassName);
            Foca.getDefault().inject(callee);
            ret = new BindAdapter(callee, outModel, advices, annoClass, def.getBind());
        }
        return ret;
    }

    private Object createModelFactory(String factoryName, String modelName) throws FocaException {
        OutModelFactory factory = Reflection.newInstance(factoryName, OutModelFactory.class);
        try {
            return factory.createParameter(modelName);
        } catch (ClassCastException e) {
            throw new XmlConsistencyException("無効なクラス指定:" + modelName, e);

        } catch (ClassNotFoundException e) {
            throw new XmlConsistencyException("無効なクラス指定:" + modelName, e);

        } catch (IllegalAccessException e) {
            throw new XmlConsistencyException("無効なクラス指定:" + modelName, e);

        } catch (Exception e) {
            throw new XmlConsistencyException(
                    "インスタンスを生成できません。生成処理呼び出し先で例外が発生しました。", e);
        }
    }

}
