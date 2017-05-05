//
// このファイルは、JavaTM Architecture for XML Binding(JAXB) Reference Implementation、v2.2.8-b130911.1802によって生成されました 
// <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a>を参照してください 
// ソース・スキーマの再コンパイル時にこのファイルの変更は失われます。 
// 生成日: 2017.04.09 時間 02:08:02 PM JST 
//


package jp.gr.java_conf.ke.namespace.foca;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>BindDef complex typeのJavaクラス。
 * 
 * <p>次のスキーマ・フラグメントは、このクラス内に含まれる予期されるコンテンツを指定します。
 * 
 * <pre>
 * &lt;complexType name="BindDef">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Bind" type="{http://www.java_conf.gr.jp/ke/namespace/foca}ItemBind" maxOccurs="1024"/>
 *       &lt;/sequence>
 *       &lt;attribute name="outModel" use="required" type="{http://www.java_conf.gr.jp/ke/namespace/foca}ClassName" />
 *       &lt;attribute name="factory" type="{http://www.java_conf.gr.jp/ke/namespace/foca}ClassName" default="jp.gr.java_conf.ke.foca.converter.DefaultFactory" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
public class BindDef {

    protected List<ItemBind> bind;
    protected String outModel;
    protected String factory;

    /**
     * Gets the value of the bind property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the bind property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getBind().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ItemBind }
     * 
     * 
     */
    public List<ItemBind> getBind() {
        if (bind == null) {
            bind = new ArrayList<ItemBind>();
        }
        return this.bind;
    }

    /**
     * outModelプロパティの値を取得します。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOutModel() {
        return outModel;
    }

    /**
     * outModelプロパティの値を設定します。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOutModel(String value) {
        this.outModel = value;
    }

    /**
     * factoryプロパティの値を取得します。
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getFactory() {
        if (factory == null) {
            return "jp.gr.java_conf.ke.foca.converter.DefaultFactory";
        } else {
            return factory;
        }
    }

    /**
     * factoryプロパティの値を設定します。
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setFactory(String value) {
        this.factory = value;
    }

}
