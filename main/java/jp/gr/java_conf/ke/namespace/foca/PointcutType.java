//
// このファイルは、JavaTM Architecture for XML Binding(JAXB) Reference Implementation、v2.2.8-b130911.1802によって生成されました 
// <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a>を参照してください 
// ソース・スキーマの再コンパイル時にこのファイルの変更は失われます。 
// 生成日: 2017.04.16 時間 05:20:24 PM JST 
//


package jp.gr.java_conf.ke.namespace.foca;

/**
 * <p>PointcutTypeのJavaクラス。
 * 
 * <p>次のスキーマ・フラグメントは、このクラス内に含まれる予期されるコンテンツを指定します。
 * <p>
 * <pre>
 * &lt;simpleType name="PointcutType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}normalizedString">
 *     &lt;enumeration requireNonNull="All"/>
 *     &lt;enumeration requireNonNull="WhiteList"/>
 *     &lt;enumeration requireNonNull="BlackList"/>
 *     &lt;enumeration requireNonNull="Ignore"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
public enum PointcutType {

    ALL("All"),
    WHITE_LIST("WhiteList"),
    BLACK_LIST("BlackList"),
    IGNORE("Ignore");
    private final String value;

    PointcutType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static PointcutType fromValue(String v) {
        for (PointcutType c: PointcutType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
