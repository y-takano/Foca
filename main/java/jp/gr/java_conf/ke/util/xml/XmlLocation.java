package jp.gr.java_conf.ke.util.xml;

import org.xml.sax.Attributes;
import org.xml.sax.Locator;
import org.xml.sax.SAXParseException;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.List;

/**
 * Created by YT on 2017/04/01.
 */

class XmlLocation implements XmlElement, Cloneable {

    private final String uri;

    private final Deque<XmlElement> msgStack = new ArrayDeque<XmlElement>();

    private final String localName;

    private final String qName;

    private Locator locator;

    private List<XmlAttribute> attributes;

    private String message;

    XmlLocation (String uri) {
        this.uri = uri;
        this.locator = null;
        this.localName = null;
        this.qName = null;
        this.attributes = null;
    }

    XmlLocation (Locator locator, String uri, String localName, String qName) {
        this(locator, uri, localName, qName, null);
    }

    XmlLocation (final SAXParseException e, String uri, String localName, String qName) {
        this(new Locator() {
            @Override
            public String getPublicId() {
                return e.getPublicId();
            }

            @Override
            public String getSystemId() {
                return e.getSystemId();
            }

            @Override
            public int getLineNumber() {
                return e.getLineNumber();
            }

            @Override
            public int getColumnNumber() {
                return e.getColumnNumber();
            }
        }, uri, localName, qName, null);
    }

    XmlLocation (Locator locator, String uri, String localName, String qName, Attributes attributes) {
        if (attributes != null) {
            this.attributes = new ArrayList<XmlAttribute>();
            for (int i=0; i<attributes.getLength(); i++) {
                this.attributes.add(new XmlAttributeImpl(
                        attributes.getURI(i),
                        attributes.getLocalName(i),
                        attributes.getQName(i),
                        attributes.getType(i),
                        attributes.getValue(i),
                        this
                ));
            }
        } else {
            this.attributes = null;
        }
        this.locator = locator;
        this.uri = uri;
        this.localName = localName;
        this.qName = qName;
    }

    public String getPublicId() {
        return locator != null ? locator.getPublicId() : "";
    }

    public String getSystemId() {
        return locator != null ? locator.getSystemId() : "";
    }

    public String getURI() {
        return uri;
    }

    public String getLocalName() {
        return localName;
    }

    public String getQName() {
        return qName;
    }

    public int getLineNumber() {
        return locator != null ? locator.getLineNumber() : -1;
    }

    public int getColumnNumber() {
        return locator != null ? locator.getColumnNumber() : -1;
    }

    public Iterable<XmlAttribute> attributes() {
        return attributes;
    }

    private static final String NL = System.getProperty("line.separator");

    public String getErrorMessage() {
        StringBuilder sb = new StringBuilder();
        sb.append(NL);
        sb.append("=======================================================");
        sb.append(NL);
        sb.append("[XML Source Error Report]");
        sb.append(NL);
        sb.append("  source=");
        sb.append(getURI());
        sb.append(NL);
        if (!msgStack.isEmpty()) {
            sb.append("- - - - - - - - - - - - - - - - - - - - - - - - - - - -");
            sb.append(NL);
            sb.append("[ERROR STACK]");
            sb.append(NL);
            for (XmlElement source : msgStack) {
                if (source instanceof XmlLocation) {
                    String message = ((XmlLocation)source).getMessage();
                    if (message != null) {
                        sb.append(" ");
                        sb.append(message);
                    }
                }
                sb.append(" (");
                sb.append(source.getSystemId());
                sb.append(":");
                sb.append(source.getLineNumber());
                sb.append(":");
                sb.append(source.getColumnNumber());
                sb.append(":<");
                sb.append(source.getQName());
                sb.append(">)");
                sb.append(NL);
            }
        }
        sb.append("=======================================================");
        return sb.toString();
    }

    String getMessage() {
        return message;
    }

    void setMessage(String msg) {
        this.message = msg;
    }

    void setNext(XmlElement source) {
        msgStack.push(source.copy());
    }

    public int hashCode() {
        int result = 17;

        result *= 31;
        result += getPublicId().hashCode();

        result *= 31;
        result += getSystemId().hashCode();

        result *= 31;
        result += getURI().hashCode();

        result *= 31;
        result += getLocalName().hashCode();

        result *= 31;
        result += getQName().hashCode();

        result *= 31;
        result += getLineNumber();

        result *= 31;
        result += getColumnNumber();

        result *= 31;
        result += Arrays.deepHashCode(attributes == null ? null : attributes.toArray());
        return result;
    }

    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null)
            return false;
        if (!(o instanceof XmlLocation))
            return false;

        XmlLocation other = (XmlLocation)o;
        return (this.getPublicId() == null ? other.getPublicId() == null : this.getPublicId().equals(other.getPublicId())) &&
                (this.getSystemId() == null ? other.getSystemId() == null : this.getSystemId().equals(other.getSystemId())) &&
                (this.getURI() == null ? other.getURI() == null : this.getURI().equals(other.getURI())) &&
                (this.getLocalName() == null ? other.getLocalName() == null : this.getLocalName().equals(other.getLocalName())) &&
                (this.getQName() == null ? other.getQName() == null : this.getQName().equals(other.getQName())) &&
                (this.attributes() == null ? other.attributes() == null : this.attributes().equals(other.attributes())) &&
                (this.getLineNumber() == other.getLineNumber()) &&
                (this.getColumnNumber() == other.getColumnNumber());
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");sb.append("publicId:");sb.append(getPublicId());
        sb.append(",");sb.append("systemId:");sb.append(getSystemId());
        sb.append(",");sb.append("uri:");sb.append(getURI());
        sb.append(",");sb.append("localName:");sb.append(getLocalName());
        sb.append(",");sb.append("qName:");sb.append(getQName());
        sb.append(",");sb.append("lineNumber:");sb.append(getLineNumber());
        sb.append(",");sb.append("columnNumber:");sb.append(getColumnNumber());
        sb.append(",");sb.append("attributes:");sb.append(attributes());
        return sb.append("}").toString();
    }

    public XmlElement copy() {
        try {
            return (XmlLocation) this.clone();
        } catch (Exception e) {
            throw new InternalError();
        }
    }
}
