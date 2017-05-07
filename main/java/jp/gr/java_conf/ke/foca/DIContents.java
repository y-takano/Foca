package jp.gr.java_conf.ke.foca;

import java.util.List;

import jp.gr.java_conf.ke.foca.xml.FocaXmlSchema;
import jp.gr.java_conf.ke.namespace.foca.Aspect;
import jp.gr.java_conf.ke.namespace.foca.Joinpoint;
import jp.gr.java_conf.ke.namespace.foca.Logger;
import jp.gr.java_conf.ke.namespace.foca.Presenter;
import jp.gr.java_conf.ke.namespace.foca.Controller;
import jp.gr.java_conf.ke.namespace.foca.DataFlow;
import jp.gr.java_conf.ke.namespace.foca.Gateway;
import jp.gr.java_conf.ke.namespace.foca.LayerContext;

/**
 * DIコンテナクラス.<br>
 * <br>
 * DIコンテナのファクトリクラスを取得する機能を持ちます。<br>
 * また、DIコンテナの設定情報を参照するインターフェースを提供します。<br>
 * 参照メソッドから取得できるJavaBeanは、原本オブジェクトのコピーです。<br>
 * <br>
 * <pre>
 *     使用例: ファクトリを取得し、JavaBeanでDI定義を設定する
 *
 *     // DI定義のルートBeanを生成
 *     LayerContext ctxt = new LayerContext();
 *     .
 *     . (JavaBeanの設定処理)
 *     .
 *
 *     // DIコンテナを生成
 *     DIContents container = DIContents.factory().create(ctxt);
 *
 *     // DI定義を設定
 *     Foca.updateDefault(container);
 * </pre>
 * <br>
 * <pre>
 *     使用例: XMLに定義されたDataFlow名を標準出力に列挙する
 *
 *     // XMLファイルからDI定義を設定
 *     Foca.updateDefault(new URL("xxx"));
 *
 *     // DIコンテナ取得
 *     DIContents container = Foca.getDefault(),getContents();
 *
 *     // 標準出力にコンテナに登録されたDataFlow名を列挙する
 *     for (DataFlow flow : container.dataflows()) {
 *         System.out.println("defined flowName: " + flow.getName()");
 *     }
 * </pre>
 *
 * @see jp.gr.java_conf.ke.foca.Foca
 * @see jp.gr.java_conf.ke.namespace.foca.LayerContext
 */
public class DIContents implements Cloneable {

	private final LayerContext context;

	DIContents(LayerContext context) {
		this.context = context;
	}

	/**
	 * DIコンテナファクトリを取得します.
	 * @return DIコンテナファクトリ
     */
	public static Factory factory() {
		return new Factory();
	}

	/**
	 * LayerContext内のAspect要素全量を返します.
	 * @return {@link Aspect}List
     */
	public List<Aspect> aspecters() {
		return copy().context.getAspect();
	}

	/**
	 * LayerContext内のDataFlow要素全量を返します.
	 * @return {@link DataFlow}List
     */
	public List<DataFlow> dataflows() {
		return copy().context.getDataFlow();
	}

	/**
	 * 指定されたname属性をもつDataFlow内のEntryPoint要素を返します.
	 * @param name DataFlowのname属性
	 * @return EntryPointの{@link Joinpoint}クラス. 見つからない場合はnull
     */
	public Nullable<Joinpoint> selectEntrypoint(String name) {
		Joinpoint entrypoint = null;
		for (DataFlow df : dataflows()) {
			if (df.getName().equals(name)) {
				entrypoint = df.getEntryPoint();
			}
		}
		return new Nullable<Joinpoint>(entrypoint);
	}

	/**
	 * 指定されたname属性をもつDataFlow内のController要素を返します.
	 * @param name DataFlowのname属性
	 * @return {@link Controller}クラス. 見つからない場合はnull
     */
	public Nullable<Controller> selectController(String name) {
		Controller ctrler = null;
		for (DataFlow df : dataflows()) {
			if (df.getName().equals(name)) {
				ctrler = df.getController();
			}
		}
		return new Nullable<Controller>(ctrler);
	}

	/**
	 * 指定されたname属性をもつDataFlow内のPresenter要素を返します.
	 * @param name DataFlowのname属性
	 * @return {@link Presenter}クラス. 見つからない場合はnull
     */
	public Nullable<Presenter> selectPresenter(String name) {
		Presenter presenter = null;
		for (DataFlow df : dataflows()) {
			if (df.getName().equals(name)) {
				presenter = df.getPresenter();
			}
		}
		return new Nullable<Presenter>(presenter);
	}

	/**
	 * 指定されたname属性をもつDataFlow内のGateway要素を返します.
	 * @param name DataFlowのname属性
	 * @return {@link Gateway}クラスList nonNull 最大サイズ:2
	 */
	public List<Gateway> selectGateway(String name) {
		List<Gateway> gateway = null;
		for (DataFlow df : dataflows()) {
			if (df.getName().equals(name)) {
				gateway = df.getGateway();
			}
		}
		return gateway;
	}

	/**
	 * 指定されたname属性をもつLogger要素を返します.
	 * @param name Loggerのname属性
	 * @return {@link Logger}クラス. nonNull
     */
	public Nullable<Logger> getLogger(String name) {
		for (Logger l : copy().context.getLogger()) {
			if (l.getName().equals(name)) return new Nullable<Logger>(l);
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

	/**
	 * DIコンテナファクトリクラス.<br>
	 * @see DIContents
	 */
	public static class Factory {
		private Factory() {}

		/**
		 * DI設定情報をもつJavaBeanからDIコンテナを生成します.
		 * @param context DI定義を設定したJavaBean
		 * @return {@link DIContents} nonNull
		 * @throws FocaException DI定義不正
		 */
		public DIContents create(LayerContext context) throws FocaException {
			return new DIContents(FocaXmlSchema.validate(context));
		}
	}

}
