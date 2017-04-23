package jp.gr.java_conf.ke.foca;

import java.lang.annotation.Annotation;

/**
 * Created by YT on 2017/04/09.
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
