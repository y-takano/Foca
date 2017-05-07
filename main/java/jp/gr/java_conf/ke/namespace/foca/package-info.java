//
// このファイルは、JavaTM Architecture for XML Binding(JAXB) Reference Implementation、v2.2.8-b130911.1802によって生成されました 
// <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a>を参照してください 
// ソース・スキーマの再コンパイル時にこのファイルの変更は失われます。 
// 生成日: 2017.04.09 時間 02:08:02 PM JST 
//

/**
 * Injection API (Core) パッケージ.<br>
 * <br>
 * {@linkplain jp.gr.java_conf.ke.foca}パッケージと協調してInjection APIを実装しています。<br>
 * <br>
 * 主なクラス:<br>
 * {@link jp.gr.java_conf.ke.namespace.foca.LayerContext} XML定義と互換性を持ち、{@link jp.gr.java_conf.ke.foca.Foca Foca}オブジェクト内に保存されます。<br>
 *
 * <pre>
 * ＜例：XMLを使用せずにDI設定情報を更新し、現在設定情報を参照する＞
 *
 *     // 新規DI設定情報を生成する
 *     LayerContext newCtxt = new LayerContext();
 *     .
 *     . (Bean設定処理)
 *     .
 *     // DI設定情報を更新する
 *     Foca.updateDefault(DIContents.factory().create(newCtxt));
 *
 *     // DI設定情報を参照する
 *     DIContents contents = Foca.getDefault().getContents();
 * </pre>
 *
 * @see jp.gr.java_conf.ke.foca.Foca
 * @see jp.gr.java_conf.ke.foca.DIContents
 */
package jp.gr.java_conf.ke.namespace.foca;
