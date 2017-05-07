package jp.gr.java_conf.ke.foca.internal.adapter;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Proxy;
import java.util.List;

import jp.gr.java_conf.ke.foca.annotation.entrance.FetchableAdapter;
import jp.gr.java_conf.ke.foca.internal.util.AspectWeaver;
import jp.gr.java_conf.ke.foca.aop.MethodAdvice;
import jp.gr.java_conf.ke.foca.FocaException;
import jp.gr.java_conf.ke.foca.InjectException;
import jp.gr.java_conf.ke.foca.annotation.entrance.InterfaceAdapter;
import jp.gr.java_conf.ke.util.Reflection;

/**
 * Created by YT on 2017/03/26.
 */

class PluginAdapter implements FetchableAdapter {

    private static final String NL = System.getProperty("line.separator");

    /**
     * 呼び出し先オブジェクト.<br>
     * アダプタ実装ロジック
     */
    private final InterfaceAdapter callee;

    <E extends Annotation> PluginAdapter(
            InterfaceAdapter callee, Class<E> marker, Object injectee, List<MethodAdvice> advices)
            throws FocaException {

        Field targetField = null;
        for (Field field : callee.getClass().getDeclaredFields()) {
            for (Annotation anno : field.getDeclaredAnnotations()) {
                if (marker.isInstance(anno)) {
                    targetField = field;
                }
            }
        }

        if (targetField == null) {
            String calleeName = callee.getClass().getCanonicalName();
            throw new InjectException(
                    "フィールドインジェクションに失敗しました。" + NL +
                            "InterfaceAdapter:'" + calleeName + "'で定義されているマーカーアノテーションは、対象クラスに実装されていません。" + NL +
                            "'" + marker.getCanonicalName() + "'を対象クラスのフィールドに修飾してください。" + NL +
                            "対象クラス : " + calleeName
            );
        }

        Object proxy = createProxy(injectee, advices);
        Reflection.setFieldValue(callee, targetField, proxy);
        this.callee = (InterfaceAdapter) createProxy(callee, advices);
    }

    public void invoke(Object param) throws Throwable {
        callee.invoke(param);
    }

    public Object fetch(Object param) throws Throwable {
        if (param != null && param instanceof Object[]) {
            throw new IllegalArgumentException("引数に配列型は認められていません。");
        }
        return ((FetchableAdapter)callee).fetch(param);
    }

    private Object createProxy(Object target, List<MethodAdvice> interceptors) throws FocaException {
        Class clazz;
        if (target instanceof FetchableAdapter) clazz = FetchableAdapter.class;
        else clazz = InterfaceAdapter.class;
        return Proxy.newProxyInstance(
                target.getClass().getClassLoader(),
                new Class[]{clazz},
                new AspectWeaver(target, interceptors));
    }

}
