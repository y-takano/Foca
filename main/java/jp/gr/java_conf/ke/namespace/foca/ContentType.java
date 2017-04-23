//
// このファイルは、JavaTM Architecture for XML Binding(JAXB) Reference Implementation、v2.2.8-b130911.1802によって生成されました 
// <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a>を参照してください 
// ソース・スキーマの再コンパイル時にこのファイルの変更は失われます。 
// 生成日: 2017.04.09 時間 04:28:31 PM JST 
//


package jp.gr.java_conf.ke.namespace.foca;

/**
 * <p>ContentTypeのJavaクラス。
 * 
 * <p>次のスキーマ・フラグメントは、このクラス内に含まれる予期されるコンテンツを指定します。
 * <p>
 * <pre>
 * &lt;simpleType name="ContentType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}normalizedString">
 *     &lt;enumeration value="UI"/>
 *     &lt;enumeration value="Web"/>
 *     &lt;enumeration value="DB"/>
 *     &lt;enumeration value="Device"/>
 *     &lt;enumeration value="Service"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
public enum ContentType {

    UI("UI"),
    WEB("Web"),
    DB("DB"),
    DEVICE("Device"),
    SERVICE("Service");
    private final String value;

    ContentType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static ContentType fromValue(String v) {
        for (ContentType c: ContentType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
