/**
 * XML解析および検証パッケージ.<br>
 * <br>
 * XMLをJavaBeanに変換する機能を実装しています。<br>
 * XSDの標準出力、XSDによる検証を行う機能を公開していますが、利用する必要はありません。<br>
 * (XMLロード時に自動的にXSD検証を実施するため、アプリケーションから呼び出す必要はありません。)<br>
 * <br>
 * 主なクラス:<br>
 * {@link jp.gr.java_conf.ke.foca.xml.FocaXmlParser} (非推奨:呼出不要) XMLファイルのロードおよびJavaBeanへの変換を行います。<br>
 * {@link jp.gr.java_conf.ke.foca.xml.FocaXmlSchema} (使用可:呼出任意) XSDによるXML検証機能、および最新のXSD定義を表示する機能を持ちます。<br>
 * <br>
 *
 * @see jp.gr.java_conf.ke.foca.Foca#updateDefault(java.net.URL)
 */
package jp.gr.java_conf.ke.foca.xml;
