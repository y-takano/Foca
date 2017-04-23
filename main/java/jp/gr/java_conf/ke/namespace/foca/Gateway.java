//
// このファイルは、JavaTM Architecture for XML Binding(JAXB) Reference Implementation、v2.2.8-b130911.1802によって生成されました 
// <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a>を参照してください 
// ソース・スキーマの再コンパイル時にこのファイルの変更は失われます。 
// 生成日: 2017.04.09 時間 04:28:31 PM JST 
//


package jp.gr.java_conf.ke.namespace.foca;

/**
 * <p>Gateway complex typeのJavaクラス。
 * 
 * <p>次のスキーマ・フラグメントは、このクラス内に含まれる予期されるコンテンツを指定します。
 * 
 * <pre>
 * &lt;complexType name="Gateway">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;choice>
 *           &lt;group ref="{http://www.java_conf.gr.jp/ke/namespace/foca}driverGroup"/>
 *           &lt;group ref="{http://www.java_conf.gr.jp/ke/namespace/foca}controllerGroup"/>
 *         &lt;/choice>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
public class Gateway {

    protected Joinpoint inputPort;
    protected Joinpoint driver;
    protected Converter converter;

    /**
     * inputPortプロパティの値を取得します。
     * 
     * @return
     *     possible object is
     *     {@link Joinpoint }
     *     
     */
    public Joinpoint getInputPort() {
        return inputPort;
    }

    /**
     * inputPortプロパティの値を設定します。
     * 
     * @param value
     *     allowed object is
     *     {@link Joinpoint }
     *     
     */
    public void setInputPort(Joinpoint value) {
        this.inputPort = value;
    }

    /**
     * driverプロパティの値を取得します。
     * 
     * @return
     *     possible object is
     *     {@link Joinpoint }
     *     
     */
    public Joinpoint getDriver() {
        return driver;
    }

    /**
     * driverプロパティの値を設定します。
     * 
     * @param value
     *     allowed object is
     *     {@link Joinpoint }
     *     
     */
    public void setDriver(Joinpoint value) {
        this.driver = value;
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
