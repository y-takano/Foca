package jp.gr.java_conf.ke.util.xml.exception;

import jp.gr.java_conf.ke.util.xml.XmlSource;

/**
 * Created by YT on 2017/04/02.
 */

public class XmlSourceExeption extends XmlException {

    private XmlSource source;

    public XmlSourceExeption(Exception e, XmlSource source) {
        super(source.getErrorMessage(), e);
        this.source = source;
    }

    public XmlSource getErrorSource() {
        return source;
    }

}
