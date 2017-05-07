package jp.gr.java_conf.ke.foca.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * {@link Logger}フィールドアノテーション.<br>
 * <br>
 * <pre>
 * 使用例:
 * <code>@Log</code>
 * private Logger logger;
 * </pre>
 * @see Logger Logger(インターフェース)
 * @see jp.gr.java_conf.ke.namespace.foca.Logger Logger(Bean)
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Log {
    /**
     * Logger要素のname属性を指定します.<br>
     * @return name属性 default:"DEFAULT"
     */
    String name() default "DEFAULT";
}
