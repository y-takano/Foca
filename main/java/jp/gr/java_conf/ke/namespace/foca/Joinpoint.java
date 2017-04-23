//
// このファイルは、JavaTM Architecture for XML Binding(JAXB) Reference Implementation、v2.2.8-b130911.1802によって生成されました 
// <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a>を参照してください 
// ソース・スキーマの再コンパイル時にこのファイルの変更は失われます。 
// 生成日: 2017.04.16 時間 05:20:24 PM JST 
//


package jp.gr.java_conf.ke.namespace.foca;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>Injector complex typeのJavaクラス。
 * 
 * <p>次のスキーマ・フラグメントは、このクラス内に含まれる予期されるコンテンツを指定します。
 * 
 * <pre>
 * &lt;complexType name="Joinpoint">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Inject" type="{http://www.java_conf.gr.jp/ke/namespace/foca}Injection"/>
 *       &lt;/sequence>
 *       &lt;attribute name="pointcut" type="{http://www.java_conf.gr.jp/ke/namespace/foca}PointcutType" default="All" />
 *       &lt;attribute name="list" type="{http://www.java_conf.gr.jp/ke/namespace/foca}WeavingList" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
public class Joinpoint {

    protected Injection inject;
    protected PointcutType pointcut;
    protected List<String> list;

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

    /**
     * pointcutプロパティの値を取得します。
     * 
     * @return
     *     possible object is
     *     {@link PointcutType }
     *     
     */
    public PointcutType getPointcut() {
        if (pointcut == null) {
            return PointcutType.ALL;
        } else {
            return pointcut;
        }
    }

    /**
     * pointcutプロパティの値を設定します。
     * 
     * @param value
     *     allowed object is
     *     {@link PointcutType }
     *     
     */
    public void setPointcut(PointcutType value) {
        this.pointcut = value;
    }

    /**
     * Gets the value of the pointcut property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the pointcut property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getList().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getList() {
        if (list == null) {
            list = new ArrayList<String>();
        }
        return this.list;
    }

}
