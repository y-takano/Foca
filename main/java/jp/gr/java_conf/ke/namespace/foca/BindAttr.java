//
// このファイルは、JavaTM Architecture for XML Binding(JAXB) Reference Implementation、v2.2.8-b130911.1802によって生成されました 
// <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a>を参照してください 
// ソース・スキーマの再コンパイル時にこのファイルの変更は失われます。 
// 生成日: 2017.04.09 時間 02:08:02 PM JST 
//


package jp.gr.java_conf.ke.namespace.foca;

/**
 * <p>BindAttr complex typeのJavaクラス。
 * 
 * <p>次のスキーマ・フラグメントは、このクラス内に含まれる予期されるコンテンツを指定します。
 * 
 * <pre>
 * &lt;complexType name="BindAttr">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *       &lt;/sequence>
 *       &lt;attribute name="field" type="{http://www.w3.org/2001/XMLSchema}Name" />
 *       &lt;attribute name="getter" type="{http://www.w3.org/2001/XMLSchema}Name" />
 *       &lt;attribute name="setter" type="{http://www.w3.org/2001/XMLSchema}Name" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
public class BindAttr {

    protected String field;
    protected String getter;
    protected String setter;

    /**
     * fieldプロパティの値を取得します。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getField() {
        return field;
    }

    /**
     * fieldプロパティの値を設定します。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setField(String value) {
        this.field = value;
    }

    /**
     * getterプロパティの値を取得します。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGetter() {
        return getter;
    }

    /**
     * getterプロパティの値を設定します。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGetter(String value) {
        this.getter = value;
    }

    /**
     * setterプロパティの値を取得します。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSetter() {
        return setter;
    }

    /**
     * setterプロパティの値を設定します。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSetter(String value) {
        this.setter = value;
    }

}
