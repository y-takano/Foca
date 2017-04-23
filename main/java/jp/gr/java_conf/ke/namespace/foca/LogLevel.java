//
// このファイルは、JavaTM Architecture for XML Binding(JAXB) Reference Implementation、v2.2.8-b130911.1802によって生成されました 
// <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a>を参照してください 
// ソース・スキーマの再コンパイル時にこのファイルの変更は失われます。 
// 生成日: 2017.04.16 時間 05:20:24 PM JST 
//


package jp.gr.java_conf.ke.namespace.foca;

/**
 * <p>LogLevelのJavaクラス。
 * 
 * <p>次のスキーマ・フラグメントは、このクラス内に含まれる予期されるコンテンツを指定します。
 * <p>
 * <pre>
 * &lt;simpleType name="LogLevel">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}normalizedString">
 *     &lt;enumeration value="DEBUG"/>
 *     &lt;enumeration value="TRACE"/>
 *     &lt;enumeration value="INFO"/>
 *     &lt;enumeration value="ERROR"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
public enum LogLevel {

    DEBUG,
    TRACE,
    INFO,
    ERROR;

    public String value() {
        return name();
    }

    public static LogLevel fromValue(String v) {
        return valueOf(v);
    }

}
