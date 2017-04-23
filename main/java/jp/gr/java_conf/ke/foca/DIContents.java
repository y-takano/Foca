package jp.gr.java_conf.ke.foca;

import java.util.List;

import jp.gr.java_conf.ke.namespace.foca.Aspect;
import jp.gr.java_conf.ke.namespace.foca.Joinpoint;
import jp.gr.java_conf.ke.namespace.foca.Logger;
import jp.gr.java_conf.ke.namespace.foca.Presenter;
import jp.gr.java_conf.ke.namespace.foca.Controller;
import jp.gr.java_conf.ke.namespace.foca.DataFlow;
import jp.gr.java_conf.ke.namespace.foca.Gateway;
import jp.gr.java_conf.ke.namespace.foca.LayerContext;

public class DIContents implements Cloneable {

	private final LayerContext context;
	private final DIContents prototype;

	DIContents(LayerContext context) {
		this.context = context;
		this.prototype = this.copy();
	}

	public static Factory factory() {
		return new Factory();
	}

	public static class Factory {
		private Factory() {}

		public DIContents create(LayerContext context) {
			return new DIContents(context);
		}
	}

	public Iterable<Aspect> aspecters() {
		return prototype.context.getAspect();
	}

	public Iterable<DataFlow> dataflows() {
		return prototype.context.getDataFlow();
	}

	public Joinpoint selectEntrypoint(String name) {
		Joinpoint entrypoint = null;
		for (DataFlow df : dataflows()) {
			if (df.getName().equals(name)) {
				entrypoint = df.getEntryPoint();
			}
		}
		return entrypoint;
	}

	public Controller selectController(String name) {
		Controller ctrler = null;
		for (DataFlow df : dataflows()) {
			if (df.getName().equals(name)) {
				ctrler = df.getController();
			}
		}
		return ctrler;
	}

	public Presenter selectPresenter(String name) {
		Presenter presenter = null;
		for (DataFlow df : dataflows()) {
			if (df.getName().equals(name)) {
				presenter = df.getPresenter();
			}
		}
		return presenter;
	}

	public List<Gateway> selectGateway(String name) {
		List<Gateway> gateway = null;
		for (DataFlow df : dataflows()) {
			if (df.getName().equals(name)) {
				gateway = df.getGateway();
			}
		}
		return gateway;
	}

	public Logger getLogger(String name) {
		for (Logger l : prototype.context.getLogger()) {
			if (l.getName().equals(name)) return l;
		}
		return null;
	}

	private DIContents copy() {
		DIContents ret;
		try {
			ret = (DIContents) clone();
		} catch(ClassCastException e) {
			throw new InternalError("ClassCastException:" + e.getMessage());
		} catch(CloneNotSupportedException e) {
			throw new InternalError("CloneNotSupportedException:" + e.getMessage());
		}
		return ret;
	}

}
