package jp.gr.java_conf.ke.foca.xml;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import jp.gr.java_conf.ke.foca.FocaException;
import jp.gr.java_conf.ke.foca.XmlConsistencyException;
import jp.gr.java_conf.ke.namespace.foca.LayerContext;

/**
 * Created by YT on 2017/04/09.
 */

public class FocaXmlSchema {

    /** W3C標準のValidatorによる検証 */
    public static LayerContext validate(URL xmlSource) throws FocaException {
        LayerContext context = downloadAndValidate(xmlSource);
        return extend(context);
    }

    /** XSD相当の内部ロジックによる検証 */
    public static LayerContext validate(LayerContext context) throws FocaException {
        XmlNodeValidator.validate(context);
        return extend(context);
    }

    public static String getXsdFromLocal() {
        return XsdString.getFromLocal();
    }

    public static String getXsdFromGithub() {
        return XsdString.getFromGithub();
    }

    public static void printXsdToSysout() {
        System.out.print(getXsdFromGithub());
    }

    // extends属性の反映と名前妥当性検証
    private static LayerContext extend(LayerContext child) throws FocaException {
        XmlNodeValidator.validateName(child);
        LayerContext parent = downloadAndValidate(child.getExtend());
        LayerContext ret;
        if (parent != null) {
            parent = extend(parent);
            XmlNodeValidator.validateName(parent);
            ret = XmlNodeValidator.margeContext(parent, child);
        } else {
            ret = child;
        }
        return ret;
    }

    private static LayerContext downloadAndValidate(String xmlURL) throws FocaException {
        if (xmlURL == null) return null;
        URL url;
        try {
            url = new URL(xmlURL);
        } catch (MalformedURLException e) {
            throw new XmlConsistencyException("extends属性に設定されたURLが不正です", e);
        }
        return downloadAndValidate(url);
    }

    private static LayerContext downloadAndValidate(URL xmlSource) throws FocaException {
        LayerContext context;
        try {
            Validator v = getW3CSchemaValidator();
            String source = download(xmlSource);
            v.validate(new StreamSource(new StringReader(source)));
            InputSource is = new InputSource(new StringReader(source));
            context = new FocaXmlParser(is).parse();
        } catch (Exception e) {
            XmlParseException ee = new XmlParseException(e);
            ee.setURL(xmlSource);
            throw ee;
        }
        return context;
    }

    // XMLソースをダウンロード
    private static String download(URL url) throws IOException {
        String ret;
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"));
            String buf;
            StringBuilder sb = new StringBuilder();
            while ((buf = br.readLine()) != null) {
                sb.append(buf);
                sb.append(System.getProperty("line.separator"));
            }
            ret = sb.toString();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch(Exception e) {}
            }
        }
        return ret;
    }

    // W3C標準のValidatorを取得
    private static Validator getW3CSchemaValidator() throws SAXException {
        SchemaFactory xsd = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        Schema schema = xsd.newSchema(new StreamSource(XsdString.getXsdReader()));
        return schema.newValidator();
    }

}
