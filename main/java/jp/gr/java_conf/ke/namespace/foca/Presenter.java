//
// このファイルは、JavaTM Architecture for XML Binding(JAXB) Reference Implementation、v2.2.8-b130911.1802によって生成されました 
// <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a>を参照してください 
// ソース・スキーマの再コンパイル時にこのファイルの変更は失われます。 
// 生成日: 2017.04.09 時間 02:08:02 PM JST 
//


package jp.gr.java_conf.ke.namespace.foca;

/**
 * <p>Presenter complex typeのJavaクラス。
 * 
 * <p>次のスキーマ・フラグメントは、このクラス内に含まれる予期されるコンテンツを指定します。
 * 
 * <pre>
 * &lt;complexType name="Presenter">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;group ref="{http://www.java_conf.gr.jp/ke/namespace/foca}presenterGroup"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
public class Presenter {

    protected Joinpoint view;
    protected Converter converter;

    /**
     * viewプロパティの値を取得します。
     * 
     * @return
     *     possible object is
     *     {@link Joinpoint }
     *     
     */
    public Joinpoint getView() {
        return view;
    }

    /**
     * viewプロパティの値を設定します。
     * 
     * @param value
     *     allowed object is
     *     {@link Joinpoint }
     *     
     */
    public void setView(Joinpoint value) {
        this.view = value;
    }

    /**
     * converterプロパティの値を取得します。
     * 
     * @return
     *     possible object is
     *     {@link Converter }
     *     
     */
    public Converter getConverter() {
        return converter;
    }

    /**
     * converterプロパティの値を設定します。
     * 
     * @param value
     *     allowed object is
     *     {@link Converter }
     *     
     */
    public void setConverter(Converter value) {
        this.converter = value;
    }

}
