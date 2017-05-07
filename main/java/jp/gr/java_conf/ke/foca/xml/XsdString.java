package jp.gr.java_conf.ke.foca.xml;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.net.URL;

/**
 * Created by YT on 2017/05/07.
 */

class XsdString {

    private static final String URL_GITHUB_MASTER =
            "https://raw.githubusercontent.com/y-takano/Foca/master/foca-dicon.xsd";

    public static String getFromGithub() {
        BufferedReader br = new BufferedReader(getXsdReader());
        StringBuilder sb = new StringBuilder();
        String buf;
        try {
            while ((buf = br.readLine()) != null) {
                sb.append(buf);
            }
            return sb.toString().replaceFirst(">", ">" + System.getProperty("line.separator"));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (Exception ee) {}
            }
        }
        return sb.toString();
    }

    public static String getFromLocal() {
        StringBuilder sb = new StringBuilder();
        sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        sb.append(System.getProperty("line.separator"));
        sb.append("<xsd:schema ");
        sb.append("xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" ");
        sb.append("xmlns:foca=\"http://www.java_conf.gr.jp/ke/namespace/foca\" ");
        sb.append("targetNamespace=\"http://www.java_conf.gr.jp/ke/namespace/foca\" ");
        sb.append("elementFormDefault=\"qualified\">");
        sb.append(System.getProperty("line.separator"));
        sb.append("    <xsd:element name=\"LayerContext\">");
        sb.append("        <xsd:complexType>");
        sb.append("            <xsd:sequence>");
        sb.append("                <xsd:element name=\"Logger\" type=\"foca:Logger\" minOccurs=\"0\" maxOccurs=\"unbounded\"/>");
        sb.append("                <xsd:element name=\"Aspect\" type=\"foca:Aspect\" minOccurs=\"0\" maxOccurs=\"unbounded\"/>");
        sb.append("                <xsd:element name=\"DataFlow\" type=\"foca:DataFlow\" minOccurs=\"1\" maxOccurs=\"unbounded\"/>");
        sb.append("            </xsd:sequence>");
        sb.append("            <xsd:attribute name=\"extend\" type=\"foca:SourceURL\"/>");
        sb.append("        </xsd:complexType>");
        sb.append("    </xsd:element>");
        sb.append("");
        sb.append("    <xsd:complexType name=\"Logger\">");
        sb.append("        <xsd:sequence></xsd:sequence>");
        sb.append("        <xsd:attribute name=\"name\" type=\"xsd:Name\" default=\"DEFAULT\"/>");
        sb.append("        <xsd:attribute name=\"level\" type=\"foca:LogLevel\" default=\"DEBUG\"/>");
        sb.append("        <xsd:attribute name=\"class\" type=\"foca:ClassName\" default=\"jp.gr.java_conf.ke.foca.aop.DefaultLogger\"/>");
        sb.append("    </xsd:complexType>");
        sb.append("");
        sb.append("    <xsd:complexType name=\"Aspect\">");
        sb.append("        <xsd:sequence></xsd:sequence>");
        sb.append("        <xsd:attribute name=\"name\" type=\"xsd:Name\" use=\"required\"/>");
        sb.append("        <xsd:attribute name=\"advice\" type=\"foca:ClassName\" use=\"required\"/>");
        sb.append("    </xsd:complexType>");
        sb.append("");
        sb.append("    <xsd:complexType name=\"DataFlow\">");
        sb.append("        <xsd:sequence>");
        sb.append("            <xsd:element name=\"EntryPoint\" type=\"foca:Joinpoint\" minOccurs=\"0\" maxOccurs=\"1\"/>");
        sb.append("            <xsd:element name=\"Controller\" type=\"foca:Controller\" minOccurs=\"0\" maxOccurs=\"1\"/>");
        sb.append("            <xsd:element name=\"Presenter\" type=\"foca:Presenter\" minOccurs=\"0\" maxOccurs=\"1\"/>");
        sb.append("            <xsd:element name=\"Gateway\" type=\"foca:Gateway\" minOccurs=\"0\" maxOccurs=\"2\"/>");
        sb.append("        </xsd:sequence>");
        sb.append("        <xsd:attribute name=\"name\" type=\"xsd:normalizedString\" use=\"required\"/>");
        sb.append("        <xsd:attribute name=\"type\" type=\"foca:ContentType\" use=\"required\"/>");
        sb.append("    </xsd:complexType>");
        sb.append("");
        sb.append("    <xsd:complexType name=\"Controller\">");
        sb.append("        <xsd:sequence>");
        sb.append("            <xsd:group ref=\"foca:controllerGroup\"/>");
        sb.append("        </xsd:sequence>");
        sb.append("    </xsd:complexType>");
        sb.append("");
        sb.append("    <xsd:complexType name=\"Presenter\">");
        sb.append("        <xsd:sequence>");
        sb.append("            <xsd:group ref=\"foca:presenterGroup\"/>");
        sb.append("        </xsd:sequence>");
        sb.append("    </xsd:complexType>");
        sb.append("");
        sb.append("    <xsd:complexType name=\"Gateway\">");
        sb.append("        <xsd:sequence>");
        sb.append("            <xsd:choice>");
        sb.append("                <xsd:group ref=\"foca:driverGroup\"/>");
        sb.append("                <xsd:group ref=\"foca:controllerGroup\"/>");
        sb.append("            </xsd:choice>");
        sb.append("        </xsd:sequence>");
        sb.append("    </xsd:complexType>");
        sb.append("");
        sb.append("    <xsd:group name=\"controllerGroup\">");
        sb.append("        <xsd:sequence>");
        sb.append("            <xsd:element name=\"InputPort\" type=\"foca:Joinpoint\" minOccurs=\"1\" maxOccurs=\"1\"/>");
        sb.append("            <xsd:element name=\"Converter\" type=\"foca:Converter\" minOccurs=\"1\" maxOccurs=\"1\"/>");
        sb.append("        </xsd:sequence>");
        sb.append("    </xsd:group>");
        sb.append("");
        sb.append("    <xsd:group name=\"presenterGroup\">");
        sb.append("        <xsd:sequence>");
        sb.append("            <xsd:element name=\"View\" type=\"foca:Joinpoint\" minOccurs=\"1\" maxOccurs=\"1\"/>");
        sb.append("            <xsd:element name=\"Converter\" type=\"foca:Converter\" minOccurs=\"1\" maxOccurs=\"1\"/>");
        sb.append("        </xsd:sequence>");
        sb.append("    </xsd:group>");
        sb.append("");
        sb.append("    <xsd:group name=\"driverGroup\">");
        sb.append("        <xsd:sequence>");
        sb.append("            <xsd:element name=\"Driver\" type=\"foca:Joinpoint\" minOccurs=\"1\" maxOccurs=\"1\"/>");
        sb.append("            <xsd:element name=\"Converter\" type=\"foca:Converter\" minOccurs=\"1\" maxOccurs=\"1\"/>");
        sb.append("        </xsd:sequence>");
        sb.append("    </xsd:group>");
        sb.append("");
        sb.append("    <xsd:complexType name=\"Converter\">");
        sb.append("        <xsd:sequence>");
        sb.append("            <xsd:choice>");
        sb.append("                <xsd:element name=\"BindDef\" type=\"foca:BindDef\" minOccurs=\"1\" maxOccurs=\"1\"/>");
        sb.append("                <xsd:element name=\"Inject\" type=\"foca:Injection\" minOccurs=\"1\" maxOccurs=\"1\"/>");
        sb.append("            </xsd:choice>");
        sb.append("        </xsd:sequence>");
        sb.append("    </xsd:complexType>");
        sb.append("");
        sb.append("    <xsd:complexType name=\"BindDef\">");
        sb.append("        <xsd:sequence>");
        sb.append("            <xsd:element name=\"Bind\" type=\"foca:ItemBind\" minOccurs=\"1\" maxOccurs=\"1024\"/>");
        sb.append("        </xsd:sequence>");
        sb.append("        <xsd:attribute name=\"outModel\" type=\"foca:ClassName\" use=\"required\"/>");
        sb.append("        <xsd:attribute name=\"factory\" type=\"foca:ClassName\" default=\"jp.gr.java_conf.ke.foca.converter.DefaultFactory\" />");
        sb.append("    </xsd:complexType>");
        sb.append("");
        sb.append("    <xsd:complexType name=\"ItemBind\">");
        sb.append("        <xsd:sequence>");
        sb.append("            <xsd:element name=\"From\" type=\"foca:BindAttr\" minOccurs=\"0\" maxOccurs=\"1\"/>");
        sb.append("            <xsd:element name=\"To\" type=\"foca:BindAttr\" minOccurs=\"0\" maxOccurs=\"1\"/>");
        sb.append("        </xsd:sequence>");
        sb.append("        <xsd:attribute name=\"converter\" type=\"foca:ClassName\" default=\"jp.gr.java_conf.ke.foca.converter.DefaultConverter\" />");
        sb.append("    </xsd:complexType>");
        sb.append("");
        sb.append("    <xsd:complexType name=\"Joinpoint\">");
        sb.append("        <xsd:sequence>");
        sb.append("            <xsd:element name=\"Inject\" type=\"foca:Injection\" minOccurs=\"1\" maxOccurs=\"1\"/>");
        sb.append("        </xsd:sequence>");
        sb.append("        <xsd:attribute name=\"pointcut\" type=\"foca:PointcutType\" default=\"All\"/>");
        sb.append("        <xsd:attribute name=\"list\" type=\"foca:WeavingList\"/>");
        sb.append("    </xsd:complexType>");
        sb.append("");
        sb.append("    <xsd:complexType name=\"Injection\">");
        sb.append("        <xsd:sequence></xsd:sequence>");
        sb.append("        <xsd:attribute name=\"class\" type=\"foca:ClassName\"/>");
        sb.append("    </xsd:complexType>");
        sb.append("");
        sb.append("    <xsd:simpleType name=\"LogLevel\">");
        sb.append("        <xsd:restriction base=\"xsd:normalizedString\">");
        sb.append("            <xsd:enumeration requireNonNull=\"DEBUG\"/>");
        sb.append("            <xsd:enumeration requireNonNull=\"TRACE\"/>");
        sb.append("            <xsd:enumeration requireNonNull=\"INFO\"/>");
        sb.append("            <xsd:enumeration requireNonNull=\"ERROR\"/>");
        sb.append("        </xsd:restriction>");
        sb.append("    </xsd:simpleType>");
        sb.append("");
        sb.append("    <xsd:simpleType name=\"ContentType\">");
        sb.append("        <xsd:restriction base=\"xsd:normalizedString\">");
        sb.append("            <xsd:enumeration requireNonNull=\"UI\"/>");
        sb.append("            <xsd:enumeration requireNonNull=\"Web\"/>");
        sb.append("            <xsd:enumeration requireNonNull=\"DB\"/>");
        sb.append("            <xsd:enumeration requireNonNull=\"Device\"/>");
        sb.append("            <xsd:enumeration requireNonNull=\"Service\"/>");
        sb.append("        </xsd:restriction>");
        sb.append("    </xsd:simpleType>");
        sb.append("");
        sb.append("    <xsd:simpleType name=\"PointcutType\">");
        sb.append("        <xsd:restriction base=\"xsd:Name\">");
        sb.append("            <xsd:enumeration requireNonNull=\"All\"/>");
        sb.append("            <xsd:enumeration requireNonNull=\"WhiteList\"/>");
        sb.append("            <xsd:enumeration requireNonNull=\"BlackList\"/>");
        sb.append("            <xsd:enumeration requireNonNull=\"Ignore\"/>");
        sb.append("        </xsd:restriction>");
        sb.append("    </xsd:simpleType>");
        sb.append("");
        sb.append("    <xsd:simpleType name=\"WeavingList\">");
        sb.append("        <xsd:list itemType=\"xsd:Name\"/>");
        sb.append("    </xsd:simpleType>");
        sb.append("");
        sb.append("    <xsd:simpleType name=\"ClassName\">");
        sb.append("        <xsd:restriction base=\"xsd:normalizedString\">");
        sb.append("            <xsd:pattern requireNonNull=\"(.*)\\.?(.*)\"/>");
        sb.append("        </xsd:restriction>");
        sb.append("    </xsd:simpleType>");
        sb.append("");
        sb.append("    <xsd:simpleType name=\"SourceURL\">");
        sb.append("        <xsd:restriction base=\"xsd:anyURI\">");
        sb.append("            <xsd:pattern requireNonNull=\".*\\.xml\"/>");
        sb.append("        </xsd:restriction>");
        sb.append("    </xsd:simpleType>");
        sb.append("");
        sb.append("    <xsd:complexType name=\"BindAttr\">");
        sb.append("        <xsd:sequence></xsd:sequence>");
        sb.append("        <xsd:attribute name=\"field\" type=\"xsd:Name\" use=\"optional\"/>");
        sb.append("        <xsd:attribute name=\"getter\" type=\"xsd:Name\" use=\"optional\"/>");
        sb.append("        <xsd:attribute name=\"setter\" type=\"xsd:Name\" use=\"optional\"/>");
        sb.append("    </xsd:complexType>");
        sb.append("");
        sb.append("</xsd:schema>");
        return sb.toString();
    }

    public static Reader getXsdReader() {
        InputStream stream = null;
        Reader r = null;
        try {
            stream = new URL(URL_GITHUB_MASTER).openStream();
            r = new InputStreamReader(stream, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
            if (stream != null) {
                try {
                    stream.close();
                } catch (IOException ee) {}
            }
            r = new StringReader(getFromLocal());
        }
        return r;
    }
}
