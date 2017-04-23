//
// このファイルは、JavaTM Architecture for XML Binding(JAXB) Reference Implementation、v2.2.8-b130911.1802によって生成されました 
// <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a>を参照してください 
// ソース・スキーマの再コンパイル時にこのファイルの変更は失われます。 
// 生成日: 2017.04.16 時間 05:20:24 PM JST 
//


package jp.gr.java_conf.ke.namespace.foca;

/**
 * <p>Aspect complex typeのJavaクラス。
 * 
 * <p>次のスキーマ・フラグメントは、このクラス内に含まれる予期されるコンテンツを指定します。
 * 
 * <pre>
 * &lt;complexType name="Aspect">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *       &lt;/sequence>
 *       &lt;attribute name="name" use="required" type="{http://www.w3.org/2001/XMLSchema}normalizedString" />
 *       &lt;attribute name="advice" use="required" type="{http://www.java_conf.gr.jp/ke/namespace/foca}ClassName" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
public class Aspect {

    protected String name;
    protected String advice;

    /**
     * nameプロパティの値を取得します。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        return name;
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
     * adviceプロパティの値を取得します。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAdvice() {
        return advice;
    }

    /**
     * adviceプロパティの値を設定します。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAdvice(String value) {
        this.advice = value;
    }

}
