package jp.gr.java_conf.ke.foca.annotation.entrance;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * {@link InterfaceAdapter}または{@link FetchableAdapter}フィールドアノテーション.<br>
 * <br>
 * Controllerの入口であることを示します。<br>
 * <pre>
 * 使用例:
 * <code>@Controller(name="hoge")</code>
 * private InterfaceAdapter adapter;
 * </pre>
 * @see InterfaceAdapter
 * @see jp.gr.java_conf.ke.namespace.foca.Controller Controller(Bean)
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Controller {
    /**
     * Controller要素のname属性を指定します.<br>
     * @return name属性 default:なし(必須)
     */
    String name();
}
