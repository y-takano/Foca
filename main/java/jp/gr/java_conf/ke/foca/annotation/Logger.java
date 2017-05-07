package jp.gr.java_conf.ke.foca.annotation;

import jp.gr.java_conf.ke.foca.Foca;

/**
 * DIコンテナに設定されたロガーを利用するAPI.<br>
 * <br>
 * ロガー毎に出力方式、文字列装飾処理を実装されており、<br>
 * アプリケーションから呼び出すことでログ書き込みを実行します。<br>
 * <br>
 * ＜Logger要素について＞<br>
 * DIコンテナにLogger要素を指定することで、任意の数のロガーを設定することができます。<br>
 * name="DEFAULT"のLogger要素が存在する場合、{@link Foca#getDefaultLogger()}の戻り値に適用されます。<br>
 * <br>
 * <pre>
 * 使用例: 複数のログ制御(XML)
 * {@literal <LayerContext>}
 *       // デフォルトロガーの上書き
 *       // Foca.getDefault().getDefaltLogger()の戻り値になります。
 *     {@literal <Logger level="DEBUG" name="DEFAULT" class="xxx.hogehoge.HogeLoggger">}
 *
 *       // カスタムロガーの登録
 *       // Foca.getDefault().getLogger("sample1")の戻り値になります。
 *     {@literal <Logger level="INFO" name="sample1" class="xxx.sample.CustomLogger" />}
 *
 *       // カスタムロガーは複数定義可能
 *     {@literal <Logger level="TRACE" name="sample2" class="xxx.sample.CustomLogger" />}
 * {@literal </LayerContext>}
 * </pre>
 * <br>
 * <pre>
 * 使用例: 複数のログ制御(java)
 *     class Sample {
 *
 *         // デフォルトロガー
 *         <code>@Log</code>
 *         private Logger syslog;
 *
 *         // 標準出力ロガー
 *         <code>@Log(name="sysout")</code>
 *         private Logger sysout;
 *
 *         // ログファイルのロガー
 *         <code>@Log(name="logfile")</code>
 *         private Logger tracelog;
 *
 *         public void execute(Object param) {
 *
 *             // ログファイル出力
 *             tracelog.trace("method in");
 *
 *             try {
 *                 // 標準出力
 *                 sysout.debug(param);
 *
 *                 ...dosomething...
 *
 *                 // デフォルトロガー出力
 *                 syslog.info("something complete.");
 *
 *             } catch (Exception e) {
 *
 *                 // デフォルトロガー出力
 *                 syslog.error(e);
 *             }
 *
 *             // ログファイル出力
 *             tracelog.trace("method in");
 *         }
 *     }
 * </pre>
 *
 * @see jp.gr.java_conf.ke.namespace.foca.Logger Logger(Bean)
 * @see jp.gr.java_conf.ke.foca.aop.DefaultLogger
 * @see Foca#getFocaDefaultLogger()
 * @see Foca#getDefaultLogger()
 */
public interface Logger {

    /**
     * ロガー運用レベル.
     */
    enum Level {
        /** デバッグ: 開発中、または障害調査に利用 */
        DEBUG,
        /** トレース: 運用中、開発中、または障害調査に利用(運用中のログサイズ最大レベル) */
        TRACE,
        /** インフォ: 運用中に利用(運用中のログサイズ標準レベル) */
        INFO,
        /** エラー: 運用中に利用(運用中のログサイズ最小レベル) */
        ERROR,
    }

    /**
     * 運用レベルを取得します.
     * @return ロガー運用レベル
     */
    Level getLevel();

    /**
     * ロガー運用レベル(DEBUG)の場合に出力されるログ書き込み.
     * @param str ログ文字列
     */
    void debug(CharSequence str);

    /**
     * ロガー運用レベル(DEBUG)の場合に出力されるログ書き込み.
     * @param obj ログに出力するオブジェクト
     */
    void debug(Object obj);

    /**
     * ロガー運用レベル(DEBUG)の場合に出力されるログ書き込み.
     * @param err ログに出力する例外オブジェクト
     * @param str ログ文字列
     */
    void debug(Throwable err, CharSequence str);

    /**
     * ロガー運用レベル(DEBUG)の場合に出力されるログ書き込み.
     * @param err ログに出力する例外オブジェクト
     * @param obj ログに出力するオブジェクト
     */
    void debug(Throwable err, Object obj);

    /**
     * ロガー運用レベル(DEBUG,TRACE)の場合に出力されるログ書き込み.
     * @param str ログ文字列
     */
    void trace(CharSequence str);

    /**
     * ロガー運用レベル(DEBUG,TRACE)の場合に出力されるログ書き込み.
     * @param obj ログに出力するオブジェクト
     */
    void trace(Object obj);

    /**
     * ロガー運用レベル(DEBUG,TRACE,INFO)の場合に出力されるログ書き込み.
     * @param str ログ文字列
     */
    void info(CharSequence str);

    /**
     * ロガー運用レベル(DEBUG,TRACE,INFO)の場合に出力されるログ書き込み.
     * @param obj ログに出力するオブジェクト
     */
    void info(Object obj);

    /**
     * 必ず出力されるログ書き込み.
     * @param err ログに出力する例外オブジェクト
     * @param str ログ文字列
     */
    void error(Throwable err, CharSequence str);

    /**
     * 必ず出力されるログ書き込み.
     * @param err ログに出力する例外オブジェクト
     * @param obj ログに出力するオブジェクト
     */
    void error(Throwable err, Object obj);
}
