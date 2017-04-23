package jp.gr.java_conf.ke.util.xml;

/**
 * Created by YT on 2017/04/02.
 */

public interface XmlElement extends XmlSource {

    String getPublicId();

    String getSystemId();

    String getLocalName();

    String getQName();

    int getLineNumber();

    int getColumnNumber();

    Iterable<XmlAttribute> attributes();

    XmlElement copy();
}
