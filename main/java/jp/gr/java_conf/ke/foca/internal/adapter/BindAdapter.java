package jp.gr.java_conf.ke.foca.internal.adapter;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import jp.gr.java_conf.ke.foca.annotation.entrance.FetchableAdapter;
import jp.gr.java_conf.ke.foca.converter.DefaultConverter;
import jp.gr.java_conf.ke.foca.internal.util.AspectWeaver;
import jp.gr.java_conf.ke.foca.converter.ParamTypeConverter;
import jp.gr.java_conf.ke.foca.aop.MethodAdvice;
import jp.gr.java_conf.ke.foca.FocaException;
import jp.gr.java_conf.ke.foca.InjectException;
import jp.gr.java_conf.ke.foca.converter.ParameterConvertException;
import jp.gr.java_conf.ke.foca.XmlConsistencyException;
import jp.gr.java_conf.ke.namespace.foca.BindAttr;
import jp.gr.java_conf.ke.util.Reflection;
import jp.gr.java_conf.ke.namespace.foca.ItemBind;

/**
 * Created by YT on 2017/03/26.
 */

class BindAdapter implements FetchableAdapter {

    private static final String NL = System.getProperty("line.separator");

    /**
     * 呼び出し先オブジェクト.<br>
     * case Controller: InputPort
     * case Presenter : View
     * case Gateway   : InputPort or Driver
     */
    private AspectWeaver weaver;

    /** 呼び出し先メソッド */
    private final Method method;

    /** データ変換定義 */
    private final Map<ItemBind, ParamTypeConverter> cnvMap;

    /**
     * 出力パラメータ.<br>
     * 呼び出し先メソッドへ渡される。
     */
    private Object outModel;

    <E extends Annotation> BindAdapter(
            Object callee, Object outModel, List<MethodAdvice> advices,
            Class<E> annotation, List<ItemBind> bindList) throws FocaException {
        Method targetMethod = null;
        for (Method m : callee.getClass().getDeclaredMethods()) {
            for (Annotation anno : m.getDeclaredAnnotations()) {
                if (annotation.isInstance(anno)) {
                    targetMethod = m;
                }
            }
        }

        if (targetMethod == null) {
            throw new XmlConsistencyException(
                    "呼び出し先のメソッドを特定できません。XMLの定義と実装を見直してください。" + NL +
                            "対象クラス: " + callee.getClass().getCanonicalName() + NL +
                            "マーカー　: " + annotation.getCanonicalName()
            );
        }

        this.cnvMap = new ConcurrentHashMap<ItemBind, ParamTypeConverter>();
        try {
            for (ItemBind bind : bindList) {
                ParamTypeConverter cnverter = Reflection.newInstance(
                        bind.getConverter(), ParamTypeConverter.class);
                if (cnverter instanceof DefaultConverter) {
                    BindAttr from = bind.getFrom();
                    BindAttr to = bind.getTo();
                    if (from == null || to == null) {
                        throw new XmlConsistencyException(
                                "Bind定義にconverterを指定しない場合、From,To要素は必須です。XMLの定義を見直してください。" + NL +
                                        "From: " + from + NL +
                                        "To  : " + to + NL +
                                        "対象クラス: " + callee.getClass().getCanonicalName() + NL +
                                        "マーカー　: " + annotation.getCanonicalName()
                        );
                    }
                }
                cnvMap.put(bind, cnverter);
            }
        } catch (FocaException e) {
            throw e;
        } catch (Exception e) {
            throw new XmlConsistencyException(
                    "Bind定義に指定されたクラスを生成できません。XMLの定義と実装を見直してください。" + NL +
                            "対象クラス: " + callee.getClass().getCanonicalName() + NL +
                            "マーカー　: " + annotation.getCanonicalName()
            );
        }
        this.weaver = new AspectWeaver(callee, advices);
        this.method = targetMethod;
        this.outModel = outModel;
    }

    public void invoke(Object param) throws Throwable {
        fetch(param);
    }

    public Object fetch(Object param) throws Throwable {
        if (param != null && param instanceof Object[]) {
            throw new IllegalArgumentException("引数に配列型は認められていません。");
        }
        try {
            for (Entry<ItemBind, ParamTypeConverter> entry : cnvMap.entrySet()) {
                outModel = entry.getValue().convert(
                        param, outModel, entry.getKey());
            }
        } catch (SecurityException e) {
            throw new InjectException(
                    "フィールドインジェクションに失敗しました。" + NL +
                            "セキュリティマネージャーを無効かするか、一部のパーミッションを許可することを検討してください。 "
                    , e);
        } catch (Exception e) {
            throw new ParameterConvertException(
                    "パラメータのデータ変換に失敗しました。" + NL +
                            "IN  Parameter: " + param.getClass().getCanonicalName() + NL +
                            "OUT Parameter: " + outModel.getClass().getCanonicalName()
                    , e);
        }
        return weaver.invoke(method, outModel);
    }
}
