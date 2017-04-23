//
// このファイルは、JavaTM Architecture for XML Binding(JAXB) Reference Implementation、v2.2.8-b130911.1802によって生成されました 
// <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a>を参照してください 
// ソース・スキーマの再コンパイル時にこのファイルの変更は失われます。 
// 生成日: 2017.04.18 時間 09:13:09 PM JST 
//


package jp.gr.java_conf.ke.namespace.foca;

/**
 * <p>Logger complex typeのJavaクラス。
 * 
 * <p>次のスキーマ・フラグメントは、このクラス内に含まれる予期されるコンテンツを指定します。
 * 
 * <pre>
 * &lt;complexType name="Logger">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *       &lt;/sequence>
 *       &lt;attribute name="name" type="{http://www.w3.org/2001/XMLSchema}normalizedString" default="" />
 *       &lt;attribute name="level" type="{http://www.java_conf.gr.jp/ke/namespace/foca}LogLevel" default="DEBUG" />
 *       &lt;attribute name="class" type="{http://www.java_conf.gr.jp/ke/namespace/foca}ClassName" default="jp.gr.java_conf.ke.foca.aop.DefaultLogger" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
public class Logger {

    protected String name;
    protected LogLevel level;
    protected String clazz;

    /**
     * nameプロパティの値を取得します。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        if (name == null) {
            return "DEFAULT";
        } else {
            return name;
        }
    }

    /**
     * nameプロパティの値を設定します。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * levelプロパティの値を取得します。
     * 
     * @return
     *     possible object is
     *     {@link LogLevel }
     *     
     */
    public LogLevel getLevel() {
        if (level == null) {
            return LogLevel.DEBUG;
        } else {
            return level;
        }
    }

    /**
     * levelプロパティの値を設定します。
     * 
     * @param value
     *     allowed object is
     *     {@link LogLevel }
     *     
     */
    public void setLevel(LogLevel value) {
        this.level = value;
    }

    /**
     * clazzプロパティの値を取得します。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getClazz() {
        if (clazz == null) {
            return "jp.gr.java_conf.ke.foca.aop.DefaultLogger";
        } else {
            return clazz;
        }
    }

    /**
     * clazzプロパティの値を設定します。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setClazz(String value) {
        this.clazz = value;
    }

}
