/**
 * Aspecter API (Option) パッケージ.<br>
 * <br>
 * Aspecter APIを実装しています。<br>
 * <br>
 * 　主なインターフェース：
 * <ul>
 *   <li> {@link jp.gr.java_conf.ke.foca.aop.MethodAdvice MethodAdvice}
 *   : 各Joinpoint要素のInterfaceAdapterが呼び出されたタイミングで実行されるAdvice処理
 * </ul>
 * <br>
 * 【参考】PointcutTypeについて:<br>
 * 　　Joinpoint毎にMethodAdviceの呼び出し方法を識別する列挙体です. <br>
 * <br>
 * 　　　・ALL: 該当のJoinPointでは全てのAspectを有効化する<br>
 * 　　　・WHITE_LIST: 該当のJoinPointのlist属性に指定された名前を持つAspectのみ有効化する<br>
 * 　　　・BLACK_LIST: 該当のJoinPointのlist属性に指定された名前を持つAspectのみ無効化する<br>
 * 　　　・IGNORE: 該当のJoinPointでは全てのAspectを有効化する<br>
 * 　　　※ pointcut省略時はALL指定扱いとする<br>
 * <br>
 * <pre>
 * 使用例（XML）：Aspectの指定方法
 *       // トレースログのアスペクタを設定
 *     {@literal <Aspect name="tracelog" advice="jp.gr.java_conf.ke.foca.aop.TraceLogAdvice"/>}
 *
 *       // オリジナルのアスペクタを設定
 *     {@literal <Aspect name="hogehoge" advice="xxx.HogeAdvice"/>}
 * </pre>
 * <pre>
 * 使用例（XML）：Joinpointの指定方法
 *       // tracelogのみ有効化
 *     {@literal <InputPort pointcut="WhiteList" list="tracelog">}
 *
 *       // tracelog, hogehogeを無効化
 *     {@literal <View pointcut="BlackList" list="tracelog hogehoge">}
 *
 *       // 全アスペクタ無効化
 *     {@literal <Driver pointcut="BlackList" list="tracelog hogehoge">}
 *
 *      // 省略時はpointcut="All"扱いとし全有効化される
 *     {@literal <Driver>}
 * </pre>
 *
 * @see jp.gr.java_conf.ke.foca.annotation.entrance.InterfaceAdapter
 * @see jp.gr.java_conf.ke.namespace.foca.Aspect
 * @see jp.gr.java_conf.ke.namespace.foca.PointcutType
 * @see jp.gr.java_conf.ke.namespace.foca.Joinpoint
 */
package jp.gr.java_conf.ke.foca.aop;
