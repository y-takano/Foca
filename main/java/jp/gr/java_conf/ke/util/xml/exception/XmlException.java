package jp.gr.java_conf.ke.util.xml.exception;

/**
 * Created by YT on 2017/04/02.
 */

public abstract class XmlException extends Exception {

    public XmlException(String msg, Exception e) {
        super(msg, e);
    }
}
