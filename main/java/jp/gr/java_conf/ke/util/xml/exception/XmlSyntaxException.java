package jp.gr.java_conf.ke.util.xml.exception;

import jp.gr.java_conf.ke.util.xml.XmlSource;

/**
 * Created by YT on 2017/03/27.
 */

public class XmlSyntaxException extends XmlSourceExeption {

    public XmlSyntaxException(Exception e, XmlSource source) {
        super(e, source);
    }
}
