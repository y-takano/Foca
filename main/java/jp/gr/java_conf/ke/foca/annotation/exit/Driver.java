package jp.gr.java_conf.ke.foca.annotation.exit;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 任意publicメソッド、またはカスタムアダプタのフィールドアノテーション.<br>
 * <br>
 * Gateway(非アプリケーション層方向)の出口であることを示します。
 * <pre>
 * 使用例:
 * <code>@Driver</code>
 * public void exec() {
 *     // do something
 * }
 * </pre>
 * <pre>
 * 使用例:
 * public class CustomAdapter implements InterfaceAdapter {
 *
 *     <code>@Driver</code>
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
 * <br>
 * 【補足】:Gatewayの方向性について<br>
 * 　・アプリケーション層方向　： アプリケーション層（ビジネスロジック）から外界へ向かう方向<br>
 * 　・非アプリケーション層方向： 外界からアプリケーション層（ビジネスロジック）へ向かう方向<br>
 * <br>
 * @see jp.gr.java_conf.ke.namespace.foca.Gateway
 */
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Driver {
}
