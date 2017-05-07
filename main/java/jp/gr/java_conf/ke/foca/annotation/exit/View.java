package jp.gr.java_conf.ke.foca.annotation.exit;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 任意publicメソッド、またはカスタムアダプタのフィールドアノテーション.<br>
 * <br>
 * Presenterの出口であることを示します。
 * <pre>
 * 使用例:
 * <code>@View</code>
 * public void exec() {
 *     // do something
 * }
 * </pre>
 * <pre>
 * 使用例:
 * public class CustomAdapter implements InterfaceAdapter {
 *
 *     <code>@View</code>
 *     private InterfaceAdapter adapter;
 *
 *     <code>@Override</code>
 *     public void invoke(Object param) {
 *        // do something
 *
 *        adapter.invoke(param);
 *
 *        // do something
 *     }
 * }
 * </pre>
 * @see jp.gr.java_conf.ke.namespace.foca.Presenter Presenter(Bean)
 */
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface View {
}
