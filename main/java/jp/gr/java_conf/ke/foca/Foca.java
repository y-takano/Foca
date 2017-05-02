package jp.gr.java_conf.ke.foca;

import java.net.URL;

import jp.gr.java_conf.ke.foca.aop.DefaultLogger;
import jp.gr.java_conf.ke.foca.aop.Logger;
import jp.gr.java_conf.ke.foca.internal.InjectRequest;
import jp.gr.java_conf.ke.foca.internal.InjectService;
import jp.gr.java_conf.ke.foca.xml.FocaXmlParser;
import jp.gr.java_conf.ke.foca.xml.XmlParseException;
import jp.gr.java_conf.ke.namespace.foca.LayerContext;

public class Foca {

	private static final String VERSION = "0.1(PROTOTYPE)";

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
			return getLogger(LoggerThreadService.defaultLoggerName());
		} catch (Exception e) {
			e.printStackTrace();
			return new DefaultLogger();
		}
	}

	public Logger getLogger(String name) {
		try {
			return logService.createLogger(contents.getLogger(name));
		} catch (Exception e) {
			e.printStackTrace();
			return new DefaultLogger();
		}
	}

	public void inject(Object target) throws FocaException {
		if (target == null) throw new NullPointerException();
		synchronized(this) {
            InjectRequest request = new InjectRequest(sourceURL, contents, target);
			injectService.execute(request);
		}
	}


	private synchronized void update(URL diconXml) throws XmlParseException {
		try {
			LayerContext context = new FocaXmlParser(diconXml).parse();
			update(DIContents.factory().create(context));
			sourceURL = diconXml;
		} catch (Exception e) {
			XmlParseException ee = new XmlParseException(e);
			ee.setURL(sourceURL);
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
				.append("{Version:")
				.append(VERSION)
				.append(",sourceURL=")
				.append(sourceURL)
				.append("}").toString();
	}
}
