//
// このファイルは、JavaTM Architecture for XML Binding(JAXB) Reference Implementation、v2.2.8-b130911.1802によって生成されました 
// <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a>を参照してください 
// ソース・スキーマの再コンパイル時にこのファイルの変更は失われます。 
// 生成日: 2017.04.18 時間 09:13:09 PM JST 
//


package jp.gr.java_conf.ke.namespace.foca;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>anonymous complex typeのJavaクラス。
 * 
 * <p>次のスキーマ・フラグメントは、このクラス内に含まれる予期されるコンテンツを指定します。
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Logger" type="{http://www.java_conf.gr.jp/ke/namespace/foca}Logger" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="Aspect" type="{http://www.java_conf.gr.jp/ke/namespace/foca}Aspect" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="DataFlow" type="{http://www.java_conf.gr.jp/ke/namespace/foca}DataFlow" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *       &lt;attribute name="extend" type="{http://www.java_conf.gr.jp/ke/namespace/foca}SourceURL" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
public class LayerContext {

    protected List<Logger> logger;
    protected List<Aspect> aspect;
    protected List<DataFlow> dataFlow;
    protected String extend;

    /**
     * Gets the value of the logger property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the logger property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getLogger().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Logger }
     * 
     * 
     */
    public List<Logger> getLogger() {
        if (logger == null) {
            logger = new ArrayList<Logger>();
        }
        return this.logger;
    }

    /**
     * Gets the value of the aspect property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the aspect property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAspect().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Aspect }
     * 
     * 
     */
    public List<Aspect> getAspect() {
        if (aspect == null) {
            aspect = new ArrayList<Aspect>();
        }
        return this.aspect;
    }

    /**
     * Gets the value of the dataFlow property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the dataFlow property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDataFlow().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DataFlow }
     * 
     * 
     */
    public List<DataFlow> getDataFlow() {
        if (dataFlow == null) {
            dataFlow = new ArrayList<DataFlow>();
        }
        return this.dataFlow;
    }

    /**
     * extendプロパティの値を取得します。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getExtend() {
        return extend;
    }

    /**
     * extendプロパティの値を設定します。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setExtend(String value) {
        this.extend = value;
    }

}
