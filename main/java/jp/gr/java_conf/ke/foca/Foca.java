package jp.gr.java_conf.ke.foca;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

public class Foca {

	private static class Holder {
		private static final Foca INSTANCE;
		static {
			INSTANCE = new Foca(new Config());
		}
	}

	public static Foca getDefault() {
		return Holder.INSTANCE;
	}

	public static Foca updateDefault(Config config) {
		Holder.INSTANCE.update(config);
		return getDefault();
	}

	public static Foca getSpecial(Config config) {
		return new Foca(config);
	}

	private Config config;

	private Foca(Config config) {
		update(config);
	}

	public Config getConf() {
		return config;
	}

	private enum Context {
		NEW,
		CONTROLLER,
		USECASE,
		PRESENTER,
		CALLBACK,
	}

	private Context context = Context.NEW;

	@SuppressWarnings("rawtypes")
	public synchronized void inject(Object target) {

		if (target == null) throw new NullPointerException();
		if (Context.CALLBACK.equals(context)) {
			context = Context.NEW;
			return;
		}

		try {
			Class clazz = target.getClass();
			Class inject = Class.forName("javax.inject.Inject");

			for (Mapper m : config.mappers()) {
				for (Field f : clazz.getDeclaredFields()) {
					Class fieldClass = f.getType();
					for (Annotation anno : f.getAnnotations()) {
						if (inject.equals(anno.annotationType())) {
							Object injectee = injectee(m, fieldClass);
							if (injectee != null) {
								InvocationHandler aspecter = m.aspctInjectee(fieldClass);
								if (aspecter != null) {
									injectee = Proxy.newProxyInstance(
											fieldClass.getClassLoader(),
								            new Class[]{ fieldClass },
								            aspecter);
								}
								inject(injectee);
								f.set(target, injectee);
								context = Context.NEW;
								return;
							}
						}
					}
				}
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@SuppressWarnings("rawtypes")
	private Object injectee(Mapper m, Class fieldClass) {
		Object injectee = m.ctrlInjectee(fieldClass);
		if (injectee == null) {
			injectee = m.uscsInjectee(fieldClass);
			if (injectee == null) {
				injectee = m.prsntInjectee(fieldClass);
				if (injectee == null) {
					injectee = m.rpstrInjectee(fieldClass);
					if (injectee == null) {
						injectee = m.clbkInjectee(fieldClass);
						context = Context.CALLBACK;
					} else {
						context = Context.PRESENTER;
					}
				} else {
					context = Context.PRESENTER;
				}
			} else {
				context = Context.USECASE;
			}
		} else {
			context = Context.CONTROLLER;
		}
		return injectee;
	}

	private void update(Config config) {
		this.config = config;
	}

}
