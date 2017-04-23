package jp.gr.java_conf.ke.foca.internal.adapter;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.List;

import jp.gr.java_conf.ke.foca.internal.AspectWeaver;
import jp.gr.java_conf.ke.foca.adapter.ParamTypeConverter;
import jp.gr.java_conf.ke.foca.aop.MethodAdvice;
import jp.gr.java_conf.ke.foca.FocaException;
import jp.gr.java_conf.ke.foca.InjectException;
import jp.gr.java_conf.ke.foca.adapter.ParameterConvertException;
import jp.gr.java_conf.ke.foca.XmlConsistencyException;
import jp.gr.java_conf.ke.foca.adapter.InterfaceAdapter;
import jp.gr.java_conf.ke.util.Reflection;
import jp.gr.java_conf.ke.namespace.foca.ItemBind;

/**
 * Created by YT on 2017/03/26.
 */

class BindAdapter implements InterfaceAdapter {

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
    private final List<ItemBind> bindList;

    /**
     * 出力パラメータ.<br>
     * 呼び出し先メソッドへ渡される。
     */
    private Object outModel;

    <E extends Annotation> BindAdapter(Object callee, Object outModel, List<MethodAdvice> advices, Class<E> annotation, List<ItemBind> bindList) throws FocaException {
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

        this.method = targetMethod;
        this.weaver = new AspectWeaver(callee, advices);
        this.bindList = bindList;
        this.outModel = outModel;
    }

    @Override
    public void invoke(Object param) throws Throwable {
        try {
            for (ItemBind bind : bindList) {
                ParamTypeConverter cnverter = Reflection.newInstance(bind.getConverter(), ParamTypeConverter.class);
                outModel = cnverter.convert(param, outModel, bind);
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
        weaver.invoke(null, method, new Object[]{param});
    }

}