package jp.gr.java_conf.ke.util.xml;

/**
 * Created by YT on 2017/04/02.
 */

class XmlAttributeImpl implements XmlAttribute {

    private final String uri;

    private final String localName;

    private final String qName;

    private final String type;

    private final String value;

    private final XmlElement parent;

    XmlAttributeImpl(String uri, String localName, String qName, String type, String value, XmlElement parent) {
        this.uri = uri;
        this.localName = localName;
        this.qName = qName;
        this.type = type;
        this.value = value;
        this.parent = parent;
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

    public String getType() {
        return type;
    }

    public String getValue() {
        return value;
    }

    public XmlElement getParent() {
        return parent;
    }

    public String getErrorMessage() {
        return parent.getErrorMessage();
    }

    public int hashCode() {
        int result = 17;

        result *= 31;
        result += getURI().hashCode();

        result *= 31;
        result += getLocalName().hashCode();

        result *= 31;
        result += getQName().hashCode();

        result *= 31;
        result += getType().hashCode();

        result *= 31;
        result += getValue().hashCode();
        return result;
    }

    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null)
            return false;
        if (!(o instanceof XmlAttributeImpl))
            return false;

        XmlAttributeImpl other = (XmlAttributeImpl)o;
        return (this.getURI() == null ? other.getURI() == null : this.getURI().equals(other.getURI())) &&
                (this.getLocalName() == null ? other.getLocalName() == null : this.getLocalName().equals(other.getLocalName())) &&
                (this.getQName() == null ? other.getQName() == null : this.getQName().equals(other.getQName())) &&
                (this.getType() == null ? other.getType() == null : this.getType().equals(other.getType())) &&
                (this.getValue() == null ? other.getValue() == null : this.getValue().equals(other.getValue()));
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");sb.append("URI:");sb.append(getURI());
        sb.append(",");sb.append("localName:");sb.append(getLocalName());
        sb.append(",");sb.append("qName:");sb.append(getQName());
        sb.append(",");sb.append("type:");sb.append(getType());
        sb.append(",");sb.append("requireNonNull:");sb.append(getValue());
        return sb.append("}").toString();
    }
}
