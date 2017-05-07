/**
 * Annotation API (Core) パッケージ.<br>
 * <br>
 * サブパッケージentrance,exitと協調し、Annotation APIを実装しています。<br>
 * <br>
 * 　{@linkplain jp.gr.java_conf.ke.foca.annotation.entrance}パッケージの主なアノテーション：
 * <ul>
 *   <li> {@link jp.gr.java_conf.ke.foca.annotation.entrance.Controller @Controller}
 *   : {@link jp.gr.java_conf.ke.namespace.foca.ContentType#UI ContentType.UI}に適用する
 *   <li> {@link jp.gr.java_conf.ke.foca.annotation.entrance.Presenter @Presenter}
 *   : {@link jp.gr.java_conf.ke.namespace.foca.ContentType#UI ContentType.UI}に適用する
 *   <li> {@link jp.gr.java_conf.ke.foca.annotation.entrance.Gateway @Gateway}
 *   : {@link jp.gr.java_conf.ke.namespace.foca.ContentType#UI ContentType.UI}以外に適用する
 * </ul>
 * 　{@linkplain jp.gr.java_conf.ke.foca.annotation.exit}パッケージの主なアノテーション：
 * <ul>
 *   <li> {@link jp.gr.java_conf.ke.foca.annotation.exit.InputPort @InputPort}
 *   : {@link jp.gr.java_conf.ke.namespace.foca.ContentType#UI ContentType.UI}に適用する
 *   <li> {@link jp.gr.java_conf.ke.foca.annotation.exit.View @View}
 *   : {@link jp.gr.java_conf.ke.namespace.foca.ContentType#UI ContentType.UI}に適用する
 *   <li> {@link jp.gr.java_conf.ke.foca.annotation.exit.Driver @Driver}
 *   : {@link jp.gr.java_conf.ke.namespace.foca.ContentType#UI ContentType.UI}以外に適用する
 * </ul>
 * 　{@linkplain jp.gr.java_conf.ke.foca.annotation}パッケージの主なアノテーション：
 * <ul>
 *   <li> {@link jp.gr.java_conf.ke.foca.annotation.Log @Log}
 *   : {@link jp.gr.java_conf.ke.namespace.foca.ContentType ContentType}に関係なくどこでも使用可能
 * </ul>
 * 　インターフェース：
 * <ul>
 *   <li> {@link jp.gr.java_conf.ke.foca.annotation.Logger Logger} :<br>
 *             使用可能アノテーション=@Log<br>
 *             コード例:<code>@Log private Logger logger;</code>
 * </ul>
 * <br>
 * 【参考】ContentTypeについて:<br>
 * 　　　DataFlowのカテゴリを識別可能とする列挙体です. <br>
 * 　　　ContentTypeは、現バージョンではシステム的なチェックには使用されません。<br>
 * 　　　（例えば、ContentType.DEVICEのDataFlowに対して@Controllerを実装してもエラーは発生しません。）<br>
 * <br>
 * 　・{@link jp.gr.java_conf.ke.namespace.foca.ContentType#UI ContentType.UI}<br>
 * 　　　基本的なMVCのDataFlowを示す.<br>
 * 　　　パターン:@Contoller->@InputPort or @Presenter->@View<br>
 * 　・{@link jp.gr.java_conf.ke.namespace.foca.ContentType#DB ContentType.DB}<br>
 * 　　　DB制御のDataFlowを示す. <br>
 * 　　　パターン:@Gateway->@InputPort or @Gateway->@Driver<br>
 * 　・{@link jp.gr.java_conf.ke.namespace.foca.ContentType#WEB ContentType.WEB}<br>
 * 　　　端末-サーバ間通信制御のDataFlowを示す. <br>
 * 　　　パターン:@Gateway->@InputPort or @Gateway->@Driver<br>
 * 　・{@link jp.gr.java_conf.ke.namespace.foca.ContentType#DEVICE ContentType.DEVICE}<br>
 * 　　　デバイス制御のDataFlowを示す. <br>
 * 　　　パターン:@Gateway->@InputPort or @Gateway->@Driver<br>
 * 　・{@link jp.gr.java_conf.ke.namespace.foca.ContentType#SERVICE ContentType.SERVICE}<br>
 * 　　　上記の何れにも属さないDataFlowを示す. <br>
 * 　　　パターン:@Gateway->@InputPort or @Gateway->@Driver<br>
 *
 * @see jp.gr.java_conf.ke.namespace.foca.DataFlow
 * @see jp.gr.java_conf.ke.namespace.foca.ContentType
 */
package jp.gr.java_conf.ke.foca.annotation;
