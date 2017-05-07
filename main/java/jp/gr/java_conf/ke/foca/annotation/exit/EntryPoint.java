package jp.gr.java_conf.ke.foca.annotation.exit;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 任意publicメソッドアノテーション.<br>
 * <br>
 * ControllerまたはPresenterまたはGatewayの起動ポイントであることを示します。<br>
 * <br>
 * <pre>
 * 使用例:
 * <code>@EntryPoint</code>
 * public void exec() {
 *     // do something
 * }
 * </pre>
 * <br>
 * 【補足】:EntryPointの呼び出しタイミングについて<br>
 * 　EntryPointは、DIが実行されたタイミングで別スレッドから呼び出されます。<br>
 * 　マルチスレッドを意識して実装をしてください。<br>
 * <br>
 *
 * @see jp.gr.java_conf.ke.namespace.foca.Controller
 * @see jp.gr.java_conf.ke.namespace.foca.Presenter
 * @see jp.gr.java_conf.ke.namespace.foca.Gateway
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface EntryPoint {
}
