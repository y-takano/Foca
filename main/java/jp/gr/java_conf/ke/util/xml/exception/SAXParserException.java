package jp.gr.java_conf.ke.util.xml.exception;

/**
 * Created by YT on 2017/04/02.
 */

public class SAXParserException extends Exception {

    public SAXParserException(String parserClass, Exception e) {
        super("factory:" + parserClass, e);
    }
}
