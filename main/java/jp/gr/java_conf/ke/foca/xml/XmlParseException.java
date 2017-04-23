package jp.gr.java_conf.ke.foca.xml;

import jp.gr.java_conf.ke.foca.FocaException;

/**
 * Created by YT on 2017/03/27.
 */

public class XmlParseException extends FocaException {

    public XmlParseException(Throwable e) {
        super("XML解析に失敗しました。", e);
    }

    public XmlParseException(String msg, Throwable e) {
        super(msg, e);
    }
}
