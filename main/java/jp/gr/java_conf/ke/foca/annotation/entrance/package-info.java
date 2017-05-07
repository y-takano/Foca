/**
 * Annotation API (Core) サブパッケージ.<br>
 * <br>
 * 親パッケージと協調し、Annotation APIを実装しています。<br>
 * <br>
 * 　主なアノテーション：
 * <ul>
 *   <li> {@link jp.gr.java_conf.ke.foca.annotation.entrance.Controller @Controller}
 *   : {@link jp.gr.java_conf.ke.namespace.foca.ContentType#UI ContentType.UI}に適用する
 *   <li> {@link jp.gr.java_conf.ke.foca.annotation.entrance.Presenter @Presenter}
 *   : {@link jp.gr.java_conf.ke.namespace.foca.ContentType#UI ContentType.UI}に適用する
 *   <li> {@link jp.gr.java_conf.ke.foca.annotation.entrance.Gateway @Gateway}
 *   : {@link jp.gr.java_conf.ke.namespace.foca.ContentType#UI ContentType.UI}以外に適用する
 * </ul>
 * 　インターフェース：
 * <ul>
 *   <li> {@link jp.gr.java_conf.ke.foca.annotation.entrance.InterfaceAdapter InterfaceAdapter}:<br>
 *       使用可能アノテーション=@Controller, @Presenter, @Gateway<br>
 *       コード例: <code>@Controller(name="sample") private InterfaceAdapter<DTO, Exception> adapter;</code>
 *   <li> {@link jp.gr.java_conf.ke.foca.annotation.entrance.FetchableAdapter FetchableAdapter}:<br>
 *       使用可能アノテーション=@Controller, @Presenter, @Gateway<br>
 *       コード例: <code>@Gateway(name="sample") private FetchableAdapter<PATAM, RETURN, Exception> adapter;</code>
 * </ul>
 *
 * @see jp.gr.java_conf.ke.foca.annotation.entrance.InterfaceAdapter
 * @see jp.gr.java_conf.ke.foca.annotation.entrance.FetchableAdapter
 */
package jp.gr.java_conf.ke.foca.annotation.entrance;
