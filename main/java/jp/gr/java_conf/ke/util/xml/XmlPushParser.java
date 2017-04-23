package jp.gr.java_conf.ke.util.xml;

import org.xml.sax.Attributes;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import jp.gr.java_conf.ke.util.xml.exception.SAXParserException;
import jp.gr.java_conf.ke.util.xml.exception.XmlException;
import jp.gr.java_conf.ke.util.xml.exception.XmlSourceExeption;
import jp.gr.java_conf.ke.util.xml.exception.XmlSyntaxException;

/**
 * Created by YT on 2017/04/01.
 */

public abstract class XmlPushParser<R> extends DefaultHandler {

    private final String xmlSource;
    private final SAXParser baseParser;

    private XmlLocation errorLocation;
    private boolean fatalError;
    private boolean error;
    private boolean warning;

    private R ret;
    private XmlException waningExp;

    protected XmlPushParser(URI xmlSource) throws SAXParserException {
        this(xmlSource.toASCIIString());
    }

    protected XmlPushParser(URL xmlSource) throws SAXParserException, XmlSourceExeption {
        this(normalize(xmlSource));
    }

    protected XmlPushParser(String xmlSource) throws SAXParserException {
        if (xmlSource == null) throw new NullPointerException();
        this.xmlSource = xmlSource;
        errorLocation = new XmlLocation(xmlSource);

        SAXParserFactory factory = SAXParserFactory.newInstance();
        String factoryClass = "[UnExpected]";
        try {
            factoryClass = factory.getClass().getCanonicalName();
            baseParser = factory.newSAXParser();
        } catch (ParserConfigurationException e) {
            throw new SAXParserException(factoryClass, e);

        } catch (SAXException e) {
            throw new SAXParserException(factoryClass, e);
        }
    }

    protected URL getURL() {
        try {
            return new URL(xmlSource);
        } catch (Exception e) {
            return null;
        }
    }

    private static String normalize(URL xmlSource) throws XmlSourceExeption {
        URI uri;
        try {
            uri = xmlSource.toURI();
        } catch (URISyntaxException e) {
            String url;
            try {
                url = String.valueOf(xmlSource);
            } catch (Throwable th) {
                url = "[UnExpected]";
            }
            throw new XmlSourceExeption(e, new XmlLocation(url));
        }
        return uri.toASCIIString();
    }

    public R parse() throws XmlSourceExeption {
        try {
            baseParser.parse(xmlSource, this);
        } catch (SAXException e) {
            throw new XmlSyntaxException(e, errorLocation);

        } catch (IOException e) {
            throw new XmlSourceExeption(e, errorLocation);
        }

        if (fatalError || error) {
            throw new XmlSyntaxException(null, errorLocation);
        }
        if (warning) {
            waningExp = new XmlSyntaxException(null, errorLocation);
        }

        return ret;
    }

    abstract protected void startParse();

    abstract protected R endParse();

    public final void startDocument ()
            throws SAXException {
        startParse();
    }

    public final void endDocument ()
            throws SAXException {
        ret = endParse();
    }

    private Locator locator;
    private XmlLocation location;
    private StringBuilder text;

    public void setDocumentLocator (Locator locator) {
        this.locator = locator;
    }

    public void startElement (String uri, String localName,
                              String qName, Attributes attributes)
            throws SAXException {
        location = new XmlLocation(locator, errorLocation.getURI(), localName, qName, attributes);
        location.setNext(new XmlLocation(locator, uri, localName, qName, attributes));
        startElement(location);
        text = new StringBuilder();
    }

    abstract protected void startElement(XmlElement element);

    public void characters (char ch[], int start, int length)
            throws SAXException {
        text.append(ch);
    }

    public void endElement (String uri, String localName, String qName)
            throws SAXException {
        XmlLocation location = new XmlLocation(locator, errorLocation.getURI(), localName, qName);
        location.setNext(new XmlLocation(locator, uri, localName, qName));
        text(location, text.toString());
        endElement(location);
    }

    abstract protected void endElement(XmlElement element);

    abstract protected void text(XmlElement element, String cdata);

    public final void warning (SAXParseException e) {
        appendWarning(e);
    }

    public final boolean hasWarning() {
        return warning;
    }

    public final XmlException getWarning() {
        return waningExp;
    }

    public final void error (SAXParseException e) {
        appendNormalError(e);
    }

    public final void fatalError (SAXParseException e) {
        appendFatalError(e);
    }

    private void appendFatalError(SAXParseException e) {
        XmlLocation loc = new XmlLocation(e,
                location.getURI(),
                location.getLocalName(),
                location.getQName());
        loc.setMessage("[FATAL]: " + e.getMessage());
        errorLocation.setNext(loc);
        fatalError = true;
    }

    private void appendNormalError(SAXParseException e) {
        XmlLocation loc = new XmlLocation(e,
                location.getURI(),
                location.getLocalName(),
                location.getQName());
        loc.setMessage("[ERROR]: " + e.getMessage());
        errorLocation.setNext(loc);
        error = true;
    }

    private void appendWarning(SAXParseException e) {
        XmlLocation loc = new XmlLocation(e,
                location.getURI(),
                location.getLocalName(),
                location.getQName());
        loc.setMessage("[WARN]: " + e.getMessage());
        errorLocation.setNext(loc);
        warning = true;
    }
}
