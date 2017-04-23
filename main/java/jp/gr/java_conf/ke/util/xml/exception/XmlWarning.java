package jp.gr.java_conf.ke.util.xml.exception;

import jp.gr.java_conf.ke.util.xml.XmlSource;

/**
 * Created by YT on 2017/04/02.
 */

public abstract class XmlWarning extends XmlSyntaxException {

    public XmlWarning(XmlSource source) {
        super(null, source);
    }

    public void throwAsException() throws XmlWarning {
        throw this;
    }
}
