package jp.gr.java_conf.ke.foca.xml;

import org.xml.sax.InputSource;

import jp.gr.java_conf.ke.namespace.foca.LayerContext;
import jp.gr.java_conf.ke.namespace.foca.Logger;
import jp.gr.java_conf.ke.util.xml.XmlElement;
import jp.gr.java_conf.ke.util.xml.XmlPushParser;
import jp.gr.java_conf.ke.util.xml.exception.SAXParserException;
import jp.gr.java_conf.ke.util.xml.exception.XmlSourceExeption;

/**
 * @deprecated フレームワーク内部で使用するためのクラスです。
 */
public class FocaXmlParser extends XmlPushParser<LayerContext> {

    /**
     * @deprecated フレームワーク内部で使用するためのクラスです。
     * @param is
     * @throws SAXParserException
     * @throws XmlSourceExeption
     */
    public FocaXmlParser(InputSource is) throws SAXParserException, XmlSourceExeption {
        super(is);
    }

    XmlNodeBuilder builder;

    @Override
    protected void startParse() {
        builder = new XmlNodeBuilder(this);
    }

    @Override
    protected LayerContext endParse() {
        LayerContext ret = builder.toXmlNode();
        if (ret.getLogger().isEmpty()) {
            ret.getLogger().add(new Logger());
        }
        return ret;
    }

    @Override
    protected void startElement(XmlElement element) {
        builder.acceptStart(element);
    }

    @Override
    protected void endElement(XmlElement element) {
        builder.acceptEnd(element);
    }

    @Override
    protected void text(XmlElement element, String cdata) {}

}
