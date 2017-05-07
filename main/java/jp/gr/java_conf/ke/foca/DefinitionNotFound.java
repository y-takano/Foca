package jp.gr.java_conf.ke.foca;

import java.lang.annotation.Annotation;

/**
 * DIコンテナにname属性の要素が存在しない.
 * @see jp.gr.java_conf.ke.foca.annotation.Log
 * @see jp.gr.java_conf.ke.foca.annotation.entrance.Controller
 * @see jp.gr.java_conf.ke.foca.annotation.entrance.Presenter
 * @see jp.gr.java_conf.ke.foca.annotation.entrance.Gateway
 * @see jp.gr.java_conf.ke.foca.annotation.exit.InputPort
 * @see jp.gr.java_conf.ke.foca.annotation.exit.View
 * @see jp.gr.java_conf.ke.foca.annotation.exit.Driver
 * @see jp.gr.java_conf.ke.foca.annotation.exit.EntryPoint
 */
public class DefinitionNotFound extends InjectException {

    private static final String NL = System.getProperty("line.separator");

    public DefinitionNotFound(String fieldName, String targetName, Class<? extends Annotation> annotation) {
        super(makeMessage(fieldName, targetName, annotation));
    }

    private static String makeMessage(String fieldName, String targetName, Class<? extends Annotation> annotation) {
        String simpleName = annotation.getSimpleName();
        String canonicalName = annotation.getCanonicalName();
        return "フィールドインジェクションに失敗しました。" + NL +
                "フィールド: " + fieldName + NL +
                simpleName + ":'" + targetName + "'で定義されている" + simpleName + "は、対象クラスに実装されていません。" + NL +
                "@" + simpleName + "(" + canonicalName + ")を対象クラスのフィールドに修飾してください。" + NL +
                "対象クラス : " + targetName;
    }

}
