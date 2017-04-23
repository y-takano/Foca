package jp.gr.java_conf.ke.util.xml;

/**
 * Created by YT on 2017/04/02.
 */

public interface XmlAttribute extends XmlSource {

    XmlElement getParent();

    String getLocalName();

    String getQName();

    String getType();

    String getValue();

}
