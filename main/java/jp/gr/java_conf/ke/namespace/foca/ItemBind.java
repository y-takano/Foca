//
// このファイルは、JavaTM Architecture for XML Binding(JAXB) Reference Implementation、v2.2.8-b130911.1802によって生成されました 
// <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a>を参照してください 
// ソース・スキーマの再コンパイル時にこのファイルの変更は失われます。 
// 生成日: 2017.04.09 時間 02:08:02 PM JST 
//


package jp.gr.java_conf.ke.namespace.foca;

/**
 * <p>ItemBind complex typeのJavaクラス。
 * 
 * <p>次のスキーマ・フラグメントは、このクラス内に含まれる予期されるコンテンツを指定します。
 * 
 * <pre>
 * &lt;complexType name="ItemBind">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="From" type="{http://www.java_conf.gr.jp/ke/namespace/foca}BindAttr"/>
 *         &lt;element name="To" type="{http://www.java_conf.gr.jp/ke/namespace/foca}BindAttr"/>
 *       &lt;/sequence>
 *       &lt;attribute name="converter" type="{http://www.java_conf.gr.jp/ke/namespace/foca}ClassName" default="jp.gr.java_conf.ke.foca.adapter.DefaultConverter" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
public class ItemBind {

    protected BindAttr from;
    protected BindAttr to;
    protected String converter;

    /**
     * fromプロパティの値を取得します。
     * 
     * @return
     *     possible object is
     *     {@link BindAttr }
     *     
     */
    public BindAttr getFrom() {
        return from;
    }

    /**
     * fromプロパティの値を設定します。
     * 
     * @param value
     *     allowed object is
     *     {@link BindAttr }
     *     
     */
    public void setFrom(BindAttr value) {
        this.from = value;
    }

    /**
     * toプロパティの値を取得します。
     * 
     * @return
     *     possible object is
     *     {@link BindAttr }
     *     
     */
    public BindAttr getTo() {
        return to;
    }

    /**
     * toプロパティの値を設定します。
     * 
     * @param value
     *     allowed object is
     *     {@link BindAttr }
     *     
     */
    public void setTo(BindAttr value) {
        this.to = value;
    }

    /**
     * converterプロパティの値を取得します。
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getConverter() {
        if (converter == null) {
            return "jp.gr.java_conf.ke.foca.adapter.DefaultConverter";
        } else {
            return converter;
        }
    }

    /**
     * converterプロパティの値を設定します。
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setConverter(String value) {
        this.converter = value;
    }

}
