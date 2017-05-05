package jp.gr.java_conf.ke.foca;

import java.net.URL;

import jp.gr.java_conf.ke.foca.aop.DefaultLogger;
import jp.gr.java_conf.ke.foca.aop.Logger;
import jp.gr.java_conf.ke.foca.internal.InjectRequest;
import jp.gr.java_conf.ke.foca.internal.InjectService;
import jp.gr.java_conf.ke.foca.xml.FocaXmlParser;
import jp.gr.java_conf.ke.foca.xml.FocaXmlSchema;
import jp.gr.java_conf.ke.foca.xml.XmlParseException;
import jp.gr.java_conf.ke.namespace.foca.LayerContext;
import jp.gr.java_conf.ke.util.Reflection;

public class Foca {

	private static final String VERSION = "1.0.4(proto)";

	private static class Holder {
		private static final Foca DEFAULT_INSTANCE;
		static {
			DEFAULT_INSTANCE = new Foca(new LoggerThreadService());
		}
	}

	public static Foca getDefault() {
		return Holder.DEFAULT_INSTANCE;
	}

	public static Foca updateDefault(URL diconXml) throws XmlParseException {
		Foca ret = getDefault();
		ret.update(diconXml);
		return ret;
	}

	public static Foca updateDefault(DIContents contents) {
		Foca ret = getDefault();
		ret.update(contents);
		return ret;
	}

	public static Foca getInstant(URL diconXml) throws XmlParseException {
		Foca ret = new Foca(getDefault().getService());
		ret.update(diconXml);
		return ret;
	}

	public static Foca getInstant(DIContents contents) {
		Foca ret = new Foca(getDefault().getService());
		ret.update(contents);
		return ret;
	}

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

	public DIContents getContents() {
		return contents;
	}

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

	public Logger getLogger(String name) {
		try {
			if (contents == null) {
				System.err.println("Foca is not initialized. return FocaDefaultLogger.");
				System.err.println(Thread.currentThread().getStackTrace()[2]);
				return Foca.getFocaDefaultLogger();
			}
			return logService.createLogger(contents.getLogger(name));
		} catch (Exception e) {
			e.printStackTrace();
			return Foca.getFocaDefaultLogger();
		}
	}

	public void inject(Object target) throws FocaException {
		if (target == null) throw new NullPointerException();
		synchronized(this) {
            InjectRequest request = new InjectRequest(sourceURL, contents, target);
			injectService.execute(request);
		}
	}

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
			LayerContext context = new FocaXmlParser(
					FocaXmlSchema.validate(diconXml)).parse();
			update(DIContents.factory().create(context));
			sourceURL = diconXml;
		} catch (Exception e) {
			XmlParseException ee = new XmlParseException(e);
			ee.setURL(diconXml);
			throw ee;
		}
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
