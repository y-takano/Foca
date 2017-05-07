package jp.gr.java_conf.ke.foca;

import java.net.URL;

import jp.gr.java_conf.ke.foca.aop.DefaultLogger;
import jp.gr.java_conf.ke.foca.annotation.Logger;
import jp.gr.java_conf.ke.foca.internal.InjectRequest;
import jp.gr.java_conf.ke.foca.internal.InjectService;
import jp.gr.java_conf.ke.foca.xml.FocaXmlSchema;
import jp.gr.java_conf.ke.foca.xml.XmlParseException;
import jp.gr.java_conf.ke.namespace.foca.LayerContext;
import jp.gr.java_conf.ke.util.Reflection;

/**
 * Injection APIファサードクラス.<br>
 * <br>
 * Injection APIの主要機能を利用するためのクラスです。<br>
 * DI設定情報の更新、DIの実行を行うインターフェースを提供します。<br>
 * <br>
 * <pre>
 * 使用例：DI設定情報を適用する
 *
 *     // ①. XMLファイルから設定する場合
 *     Foca.updateDefault(new URL("xxx"));
 *
 *     // ②. JavaBeanから設定する場合
 *     LayoutContext ctxt = new LayoutContext();
 *     .
 *     . (JavaBeanの設定処理)
 *     .
 *     Foca.updateDefault(DIContents.factory().create(ctxt));
 * </pre>
 * <pre>
 * 使用例：対象インスタンスにDIを実行する
 *
 *     // ①. クラスオブジェクトからDI適用後のインスタンスを取得
 *     SomeObj obj = Foca.getDefault().createInstance(SomeObj.class);
 *
 *     // ②. クラスインスタンスにDIを適用
 *     SomeObj obj = new SomeObj();
 *     Foca.getDefault().inject(obj);
 * </pre>
 */
public class Foca {

	private static final String VERSION = "1.0.0(beta)";

	private static class Holder {
		private static final Foca DEFAULT_INSTANCE;
		static {
			DEFAULT_INSTANCE = new Foca(new LoggerThreadService());
		}
	}

    /**
     * シングルトン(JVMに対して唯一の)インスタンスを取得します.
     * @return シングルトンインスタンス
     */
	public static Foca getDefault() {
		return Holder.DEFAULT_INSTANCE;
	}

    /**
     * シングルトン(JVMに対して唯一の)インスタンスが保有するDI設定情報を更新します.
     * @param diconXml DI設定情報ファイルのURL
     * @return シングルトンインスタンス
     * @throws XmlParseException XMLのURLまたは定義内容が不当である場合, またはネットワーク不通
     */
	public static Foca updateDefault(URL diconXml) throws XmlParseException {
		Foca ret = getDefault();
		ret.update(diconXml);
		return ret;
	}

    /**
     * シングルトン(JVMに対して唯一の)インスタンスが保有するDI設定情報を更新します.
     * @param contents DIコンテナ
     * @return シングルトンインスタンス
     */
	public static Foca updateDefault(DIContents contents) {
		Foca ret = getDefault();
		ret.update(contents);
		return ret;
	}

    /**
     * 指定されたDI設定情報を保有する新しいインスタンスを生成します.<br>
     * @param diconXml DI設定情報ファイルのURL
     * @return 新しいインスタンス
     * @throws XmlParseException DI設定情報ファイルのURL
     */
	public static Foca getInstant(URL diconXml) throws XmlParseException {
		Foca ret = new Foca(getDefault().getService());
		ret.update(diconXml);
		return ret;
	}

    /**
     * 指定されたDIコンテナを保有する新しいインスタンスを生成します.<br>
     * @param contents DIコンテナ
     * @return 新しいインスタンス
     */
	public static Foca getInstant(DIContents contents) {
		Foca ret = new Foca(getDefault().getService());
		ret.update(contents);
		return ret;
	}

    /**
     * Focaデフォルトロガーを取得します.
     * @return {@link DefaultLogger}インスタンス
     */
	public static Logger getFocaDefaultLogger() {
		return new DefaultLogger();
	}

	private URL sourceURL;
	private DIContents contents;
	private LoggerThreadService logService;
	private InjectService injectService;

	private Foca(LoggerThreadService logService) {
		this.logService = logService;
		this.injectService = new InjectService();
	}

    /**
     * DIコンテナを取得します.
     * @return DIコンテナ
     */
	public DIContents getContents() {
		return contents;
	}

    /**
     * DIコンテナに設定されたデフォルトロガーを取得します.<br>
     * <br>
     * 未定義時は{@link Foca#getFocaDefaultLogger()}を返します。
     * @return ロガー nonNull
     */
	public Logger getDefaultLogger() {
		try {
			if (contents == null) {
				System.err.println("Foca is not initialized. return FocaDefaultLogger.");
				System.err.println(Thread.currentThread().getStackTrace()[2]);
				return Foca.getFocaDefaultLogger();
			}
			return getLogger(LoggerThreadService.defaultLoggerName());
		} catch (Exception e) {
			e.printStackTrace();
			return Foca.getFocaDefaultLogger();
		}
	}

    /**
     * DIコンテナに設定されたロガーを取得します.<br>
     * <br>
     * 見つからない場合はnullを返します。
     *
     * @param name ロガー名
     * @return ロガー
     */
	public Logger getLogger(String name) {
		try {
			if (contents == null) {
				System.err.println("Foca is not initialized. return FocaDefaultLogger.");
				System.err.println(Thread.currentThread().getStackTrace()[2]);
				return Foca.getFocaDefaultLogger();
			}
			return logService.createLogger(contents.getLogger(name).rawValue());
		} catch (Exception e) {
			e.printStackTrace();
			return Foca.getFocaDefaultLogger();
		}
	}

    /**
     * DIを実行します.
     * @param target DI対象インスタンス
     * @throws FocaException DIコンテナ不整合、または依存性の設定箇所の矛盾発生時
     */
	public void inject(Object target) throws FocaException {
		if (target == null) throw new NullPointerException();
		synchronized(this) {
            InjectRequest request = new InjectRequest(sourceURL, contents, target);
			injectService.execute(request);
		}
	}

    /**
     * デフォルトコンストラクタを使用してDIを実行します.
     * <br>
     * デフォルトコンストラクタがpublicでない場合は例外とします.
     *
     * @param target DI対象クラス
     * @param <E> DI対象クラス
     * @return DI処理済インスタンス
     * @throws FocaException クラス生成または{@link #inject(Object)}の例外
     * @see #inject(Object)
     */
	public <E> E createInstance(Class<E> target) throws FocaException {
		if (target == null) throw new NullPointerException();
		E instance;
		try {
			instance = Reflection.newInstance(target);
		} catch (Exception e) {
			FocaException ee = new InjectException("クラスの生成に失敗しました。", e);
			ee.setURL(sourceURL);
			throw ee;
		}
		inject(instance);
		return instance;
	}

	private synchronized void update(URL diconXml) throws XmlParseException {
		try {
			LayerContext context = FocaXmlSchema.validate(diconXml);
            update(new DIContents(context));
		} catch (Exception e) {
			XmlParseException ee = new XmlParseException(e);
			ee.setURL(diconXml);
			throw ee;
		}
        sourceURL = diconXml;
	}

	private synchronized void update(DIContents contents) {
		this.contents = contents;
	}

	private LoggerThreadService getService() {
		return logService;
	}

	public String toString() {
		return new StringBuilder()
				.append("{Version:\"")
				.append(VERSION)
				.append("\", sourceURL=\"")
				.append(sourceURL==null ? "[embedded]":sourceURL)
				.append("\"}").toString();
	}
}
