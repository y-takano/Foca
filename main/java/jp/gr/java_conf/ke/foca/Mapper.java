package jp.gr.java_conf.ke.foca;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

public abstract class Mapper {

	private Map<Class<?>, InvocationHandler> aspecters = new HashMap<Class<?>, InvocationHandler>();
	private Map<Class<?>, Object> controllers = new HashMap<Class<?>, Object>();
	private Map<Class<?>, Object> usecases = new HashMap<Class<?>, Object>();
	private Map<Class<?>, Object> repositories = new HashMap<Class<?>, Object>();
	private Map<Class<?>, Object> prenters = new HashMap<Class<?>, Object>();
	private Map<Class<?>, Object> callbacks = new HashMap<Class<?>, Object>();

	protected Mapper() {}

	protected final void aspecter(Class<?> aspecter, InvocationHandler handler) {
		validate(aspecter, handler);
		aspecters.put(aspecter, handler);
	}

	protected final void controller(Class<?> api, Object injectee) {
		validate(api, injectee);
		controllers.put(api, injectee);
	}

	protected final void usecase(Class<?> inputPort, Object injectee) {
		validate(inputPort, injectee);
		usecases.put(inputPort, injectee);
	}

	protected final void repository(Class<?> outputPort, Object injectee) {
		validate(outputPort, injectee);
		repositories.put(outputPort, injectee);
	}

	protected final void presenter(Class<?> outputPort, Object injectee) {
		validate(outputPort, injectee);
		prenters.put(outputPort, injectee);
	}

	protected final void callback(Class<?> callback, Object injectee) {
		validate(callback, injectee);
		callbacks.put(callback, injectee);
	}

	protected abstract void bind();

	InvocationHandler aspctInjectee(Class<?> aspecter) {
		return aspecters.get(aspecter);
	}

	Object ctrlInjectee(Class<?> api) {
		return controllers.get(api);
	}

	Object uscsInjectee(Class<?> inputPort) {
		return usecases.get(inputPort);
	}

	Object rpstrInjectee(Class<?> outputPort) {
		return repositories.get(outputPort);
	}

	Object prsntInjectee(Class<?> outputPort) {
		return prenters.get(outputPort);
	}

	Object clbkInjectee(Class<?> callback) {
		return callbacks.get(callback);
	}

	private void validate(Class<?> interFace, Object injectee) {
		if (interFace == null || injectee == null) throw new NullPointerException();
		if (!interFace.isInterface()) throw new RuntimeException(interFace.getCanonicalName() + " is not interface");
		if (!(interFace.isAssignableFrom(injectee.getClass()) && Modifier.isAbstract(injectee.getClass().getModifiers()))) {
			throw new RuntimeException(injectee.getClass().getCanonicalName() + " is not implementation of " + interFace.getCanonicalName());
		}
	}

	public String toString() {
		return new StringBuilder()
			.append("{")
			.append("aspecters=").append(aspecters)
			.append(",controllers=").append(controllers)
			.append(",usecases=").append(usecases)
			.append(",repositories=").append(repositories)
			.append(",prenters=").append(prenters)
			.append(",callbacks=").append(callbacks)
			.append("}").toString();
	}
}
