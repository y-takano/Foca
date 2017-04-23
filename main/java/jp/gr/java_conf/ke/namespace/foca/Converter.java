//
// このファイルは、JavaTM Architecture for XML Binding(JAXB) Reference Implementation、v2.2.8-b130911.1802によって生成されました 
// <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a>を参照してください 
// ソース・スキーマの再コンパイル時にこのファイルの変更は失われます。 
// 生成日: 2017.04.09 時間 02:08:02 PM JST 
//


package jp.gr.java_conf.ke.namespace.foca;

/**
 * <p>Converter complex typeのJavaクラス。
 * 
 * <p>次のスキーマ・フラグメントは、このクラス内に含まれる予期されるコンテンツを指定します。
 * 
 * <pre>
 * &lt;complexType name="Converter">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;choice>
 *           &lt;element name="BindDef" type="{http://www.java_conf.gr.jp/ke/namespace/foca}BindDef"/>
 *           &lt;element name="Inject" type="{http://www.java_conf.gr.jp/ke/namespace/foca}Injection"/>
 *         &lt;/choice>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
public class Converter {

    protected BindDef bindDef;
    protected Injection inject;

    /**
     * bindDefプロパティの値を取得します。
     * 
     * @return
     *     possible object is
     *     {@link BindDef }
     *     
     */
    public BindDef getBindDef() {
        return bindDef;
    }

    /**
     * bindDefプロパティの値を設定します。
     * 
     * @param value
     *     allowed object is
     *     {@link BindDef }
     *     
     */
    public void setBindDef(BindDef value) {
        this.bindDef = value;
    }

    /**
     * injectプロパティの値を取得します。
     * 
     * @return
     *     possible object is
     *     {@link Injection }
     *     
     */
    public Injection getInject() {
        return inject;
    }

    /**
     * injectプロパティの値を設定します。
     * 
     * @param value
     *     allowed object is
     *     {@link Injection }
     *     
     */
    public void setInject(Injection value) {
        this.inject = value;
    }

}
