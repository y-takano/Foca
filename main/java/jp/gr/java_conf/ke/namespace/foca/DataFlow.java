//
// このファイルは、JavaTM Architecture for XML Binding(JAXB) Reference Implementation、v2.2.8-b130911.1802によって生成されました 
// <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a>を参照してください 
// ソース・スキーマの再コンパイル時にこのファイルの変更は失われます。 
// 生成日: 2017.04.09 時間 04:28:31 PM JST 
//


package jp.gr.java_conf.ke.namespace.foca;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>DataFlow complex typeのJavaクラス。
 * 
 * <p>次のスキーマ・フラグメントは、このクラス内に含まれる予期されるコンテンツを指定します。
 * 
 * <pre>
 * &lt;complexType name="DataFlow">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="EntryPoint" type="{http://www.java_conf.gr.jp/ke/namespace/foca}Joinpoint" minOccurs="0"/>
 *         &lt;element name="Controller" type="{http://www.java_conf.gr.jp/ke/namespace/foca}Controller" minOccurs="0"/>
 *         &lt;element name="Presenter" type="{http://www.java_conf.gr.jp/ke/namespace/foca}Presenter" minOccurs="0"/>
 *         &lt;element name="Gateway" type="{http://www.java_conf.gr.jp/ke/namespace/foca}Gateway" maxOccurs="2" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="type" use="required" type="{http://www.java_conf.gr.jp/ke/namespace/foca}ContentType" />
 *       &lt;attribute name="name" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
public class DataFlow {

    protected Joinpoint entryPoint;
    protected Controller controller;
    protected Presenter presenter;
    protected List<Gateway> gateway;
    protected ContentType type;
    protected String name;

    /**
     * entryPointプロパティの値を取得します。
     * 
     * @return
     *     possible object is
     *     {@link Joinpoint }
     *     
     */
    public Joinpoint getEntryPoint() {
        return entryPoint;
    }

    /**
     * entryPointプロパティの値を設定します。
     * 
     * @param value
     *     allowed object is
     *     {@link Joinpoint }
     *     
     */
    public void setEntryPoint(Joinpoint value) {
        this.entryPoint = value;
    }

    /**
     * controllerプロパティの値を取得します。
     * 
     * @return
     *     possible object is
     *     {@link Controller }
     *     
     */
    public Controller getController() {
        return controller;
    }

    /**
     * controllerプロパティの値を設定します。
     * 
     * @param value
     *     allowed object is
     *     {@link Controller }
     *     
     */
    public void setController(Controller value) {
        this.controller = value;
    }

    /**
     * presenterプロパティの値を取得します。
     * 
     * @return
     *     possible object is
     *     {@link Presenter }
     *     
     */
    public Presenter getPresenter() {
        return presenter;
    }

    /**
     * presenterプロパティの値を設定します。
     * 
     * @param value
     *     allowed object is
     *     {@link Presenter }
     *     
     */
    public void setPresenter(Presenter value) {
        this.presenter = value;
    }

    /**
     * Gets the requireNonNull of the gateway property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the gateway property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getGateway().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Gateway }
     * 
     * 
     */
    public List<Gateway> getGateway() {
        if (gateway == null) {
            gateway = new ArrayList<Gateway>();
        }
        return this.gateway;
    }

    /**
     * typeプロパティの値を取得します。
     * 
     * @return
     *     possible object is
     *     {@link ContentType }
     *     
     */
    public ContentType getType() {
        return type;
    }

    /**
     * typeプロパティの値を設定します。
     * 
     * @param value
     *     allowed object is
     *     {@link ContentType }
     *     
     */
    public void setType(ContentType value) {
        this.type = value;
    }

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

}
