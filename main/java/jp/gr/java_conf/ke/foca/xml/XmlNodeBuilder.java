package jp.gr.java_conf.ke.foca.xml;

import org.xml.sax.SAXParseException;

import java.net.URL;

import jp.gr.java_conf.ke.namespace.foca.Aspect;
import jp.gr.java_conf.ke.namespace.foca.BindAttr;
import jp.gr.java_conf.ke.namespace.foca.BindDef;
import jp.gr.java_conf.ke.namespace.foca.ContentType;
import jp.gr.java_conf.ke.namespace.foca.Controller;
import jp.gr.java_conf.ke.namespace.foca.Converter;
import jp.gr.java_conf.ke.namespace.foca.DataFlow;
import jp.gr.java_conf.ke.namespace.foca.Gateway;
import jp.gr.java_conf.ke.namespace.foca.Injection;
import jp.gr.java_conf.ke.namespace.foca.Joinpoint;
import jp.gr.java_conf.ke.namespace.foca.ItemBind;
import jp.gr.java_conf.ke.namespace.foca.LayerContext;
import jp.gr.java_conf.ke.namespace.foca.LogLevel;
import jp.gr.java_conf.ke.namespace.foca.Logger;
import jp.gr.java_conf.ke.namespace.foca.Presenter;
import jp.gr.java_conf.ke.util.xml.XmlAttribute;
import jp.gr.java_conf.ke.util.xml.XmlElement;
import jp.gr.java_conf.ke.util.xml.XmlPushParser;

/**
 * Created by YT on 2017/04/09.
 */

class XmlNodeBuilder {

    private XmlPushParser<LayerContext> parser;
    private LayerContext context;
    private int depth = 0;

    private Logger logger;

    private Aspect aspect;

    private DataFlow dataflow;

    private Controller controller;
    private Presenter presenter;
    private Gateway gateway;
    private Joinpoint entryPoint;

    private Converter gConverter;
    private Joinpoint gInputPort;
    private Joinpoint driver;

    private Converter cConverter;
    private Joinpoint cInputPort;

    private Converter pConverter;
    private Joinpoint view;

    private BindDef bindDef;

    private ItemBind bind;

    private BindAttr from;
    private BindAttr to;

    XmlNodeBuilder(XmlPushParser<LayerContext> parser) {
        this.parser = parser;
    }

    LayerContext toXmlNode() {
        return context;
    }

    void acceptEnd(XmlElement element) {
        depth--;
    }

    void acceptStart(XmlElement element) {
        String qName = element.getQName();

        if (depth == 0 && !qName.equals("LayerContext")) {
            callFatalError("{LayerContext}ではないXMLエレメントが定義されています", element);
        } else {
            for (XmlAttribute attr : element.attributes()) {
                if (attr.getQName().equals("extend")) {
                    try {
                        URL url = new URL(attr.getValue());
                        this.context = new FocaXmlParser(url).parse();
                    } catch (Exception e) {
                        callFatalError(e.getMessage(), element);
                    }
                    this.context = new LayerContext();
                }
            }
            if (this.context == null) {
                this.context = new LayerContext();
            }
        }

        if (depth == 1) {
            if (qName.equals("DataFlow")) {
                dataflow = new DataFlow();
                for (XmlAttribute attr : element.attributes()) {
                    if (attr.getQName().equals("name")) {
                        dataflow.setName(attr.getValue());

                    } else if (attr.getQName().equals("type")) {
                        dataflow.setType(ContentType.fromValue(attr.getValue()));
                    }
                }
                context.getDataFlow().add(dataflow);

            } else if (qName.equals("Logger")) {
                logger = new Logger();
                for (XmlAttribute attr : element.attributes()) {
                    if (attr.getQName().equals("name")) {
                        logger.setName(attr.getValue());

                    } else if (attr.getQName().equals("level")) {
                        logger.setLevel(LogLevel.fromValue(attr.getValue()));

                    } else if (attr.getQName().equals("class")) {
                        logger.setClazz(attr.getValue());
                    }
                }
                context.getLogger().add(logger);

            } else if (qName.equals("Aspect")) {
                aspect = new Aspect();
                for (XmlAttribute attr : element.attributes()) {
                    if (attr.getQName().equals("name")) {
                        aspect.setName(attr.getValue());

                    } else if (attr.getQName().equals("advice")) {
                        aspect.setAdvice(attr.getValue());

                    }
                }
                context.getAspect().add(aspect);

            } else {
                callError("{DataFlow}ではないXMLエレメントが定義されています", element);
            }
        }

        if (depth == 2 && dataflow != null) {
            if (qName.equals("Controller")) {
                controller = new Controller();
                presenter = null;
                gateway = null;
                entryPoint = null;
                dataflow.setController(controller);

            } else if (qName.equals("Presenter")) {
                controller = null;
                presenter = new Presenter();
                gateway = null;
                entryPoint = null;
                dataflow.setPresenter(presenter);

            } else if (qName.equals("Gateway")) {
                controller = null;
                presenter = null;
                gateway = new Gateway();
                entryPoint = null;
                dataflow.getGateway().add(gateway);

            } else if (qName.equals("EntryPoint")) {
                controller = null;
                presenter = null;
                gateway = null;
                entryPoint = new Joinpoint();
                dataflow.setEntryPoint(entryPoint);

            } else {
                callError("{Controller, Presenter, Gateway}ではないXMLエレメントが定義されています", element);
            }
        }

        if (depth == 3 && controller != null) {
            if (qName.equals("Converter")) {
                cConverter = new Converter();
                cInputPort = null;
                pConverter = null;
                view = null;
                gConverter = null;
                gInputPort = null;
                driver = null;
                controller.setConverter(cConverter);

            } else if (qName.equals("InputPort")) {
                cConverter = null;
                cInputPort = new Joinpoint();
                pConverter = null;
                view = null;
                gConverter = null;
                gInputPort = null;
                driver = null;
                controller.setInputPort(cInputPort);

            } else {
                callError("{Converter, InputPort}ではないXMLエレメントが定義されています", element);
            }

        } else if (depth == 3 && presenter != null) {
            if (qName.equals("Converter")) {
                cConverter = null;
                cInputPort = null;
                pConverter = new Converter();
                view = null;
                gConverter = null;
                gInputPort = null;
                driver = null;
                presenter.setConverter(pConverter);

            } else if (qName.equals("View")) {
                cConverter = null;
                cInputPort = null;
                pConverter = null;
                view = new Joinpoint();
                gConverter = null;
                gInputPort = null;
                driver = null;
                presenter.setView(view);

            } else {
                callError("{Converter, View}ではないXMLエレメントが定義されています", element);
            }

        } else if (depth == 3 && gateway != null) {
            if (qName.equals("Converter")) {
                cConverter = null;
                cInputPort = null;
                pConverter = null;
                view = null;
                gConverter = new Converter();
                gInputPort = null;
                driver = null;
                gateway.setConverter(gConverter);

            } else if (qName.equals("InputPort")) {
                cConverter = null;
                cInputPort = null;
                pConverter = null;
                view = null;
                gConverter = null;
                gInputPort = new Joinpoint();
                driver = null;
                view = null;
                gateway.setInputPort(gInputPort);

            } else if (qName.equals("Driver")) {
                cConverter = null;
                cInputPort = null;
                pConverter = null;
                view = null;
                gConverter = null;
                gInputPort = null;
                view = null;
                driver = new Joinpoint();
                gateway.setDriver(driver);

            } else {
                callError("{Converter, InputPort, Driver}ではないXMLエレメントが定義されています", element);
            }

        } else if (depth == 3 && entryPoint != null) {
            if (qName.equals("Inject")) {
                Injection injection = new Injection();
                String clazz = element.attributes().iterator().next().getValue();
                injection.setClazz(clazz);
                entryPoint.setInject(injection);

            } else {
                callError("{Inject}ではないXMLエレメントが定義されています", element);
            }

        }

        if (depth == 4 && cConverter != null) {
            if (qName.equals("Inject")) {
                Injection inject = new Injection();
                String clazz = element.attributes().iterator().next().getValue();
                inject.setClazz(clazz);
                cConverter.setInject(inject);

            } else if (qName.equals("BindDef")) {
                bindDef = new BindDef();
                String clazz = element.attributes().iterator().next().getValue();
                bindDef.setOutModel(clazz);
                cConverter.setBindDef(bindDef);

            } else {
                callError("{BindDef, Inject}ではないXMLエレメントが定義されています", element);
            }

        } else if (depth == 4 && cInputPort != null) {
            if (qName.equals("Inject")) {
                Injection inject = new Injection();
                String clazz = element.attributes().iterator().next().getValue();
                inject.setClazz(clazz);
                cInputPort.setInject(inject);

            } else {
                callError("{Inject}ではないXMLエレメントが定義されています", element);
            }

        } else if (depth == 4 && pConverter != null) {
            if (qName.equals("Inject")) {
                Injection inject = new Injection();
                String clazz = element.attributes().iterator().next().getValue();
                inject.setClazz(clazz);
                pConverter.setInject(inject);

            } else if (qName.equals("BindDef")) {
                bindDef = new BindDef();
                String clazz = element.attributes().iterator().next().getValue();
                bindDef.setOutModel(clazz);
                pConverter.setBindDef(bindDef);

            } else {
                callError("{BindDef, Inject}ではないXMLエレメントが定義されています", element);
            }

        } else if (depth == 4 && view != null) {
            if (qName.equals("Inject")) {
                Injection inject = new Injection();
                String clazz = element.attributes().iterator().next().getValue();
                inject.setClazz(clazz);
                view.setInject(inject);

            } else {
                callError("{Inject}ではないXMLエレメントが定義されています", element);
            }

        } else if (depth == 4 && gConverter != null) {
            if (qName.equals("Inject")) {
                Injection inject = new Injection();
                String clazz = element.attributes().iterator().next().getValue();
                inject.setClazz(clazz);
                gConverter.setInject(inject);

            } else if (qName.equals("BindDef")) {
                bindDef = new BindDef();
                for (XmlAttribute attr : element.attributes()) {
                    if (attr.getQName().equals("outModel")) {
                        bindDef.setOutModel(attr.getValue());
                    } else if (attr.getQName().equals("factory")) {
                        String factory = attr.getValue();
                        if (factory != null && factory.length() > 0) {
                            bindDef.setFactory(factory);
                        }
                    }
                }
                gConverter.setBindDef(bindDef);

            } else {
                callError("{BindDef, Inject}ではないXMLエレメントが定義されています", element);
            }

        } else if (depth == 4 && gInputPort != null) {
            if (qName.equals("Inject")) {
                Injection inject = new Injection();
                String clazz = element.attributes().iterator().next().getValue();
                inject.setClazz(clazz);
                gInputPort.setInject(inject);

            } else {
                callError("{Inject}ではないXMLエレメントが定義されています", element);
            }

        } else if (depth == 4 && driver != null) {
            if (qName.equals("Inject")) {
                Injection inject = new Injection();
                String clazz = element.attributes().iterator().next().getValue();
                inject.setClazz(clazz);
                driver.setInject(inject);

            } else {
                callError("{Inject}ではないXMLエレメントが定義されています", element);
            }
        }

        if (depth == 5 && bindDef != null) {
            if (qName.equals("Bind")) {
                bind = new ItemBind();
                if (element.attributes().iterator().hasNext()) {
                    XmlAttribute attr = element.attributes().iterator().next();
                    if (null != attr.getValue() && attr.getValue().length() > 0) {
                        bind.setConverter(attr.getValue());
                    }
                }
                bindDef.getBind().add(bind);
            } else {
                callError("{Bind}ではないXMLエレメントが定義されています", element);
            }
        }

        if (depth == 6 && bind != null) {
            if (qName.equals("From")) {
                from = new BindAttr();
                to = null;
                bind.setFrom(from);
                for (XmlAttribute attr : element.attributes()) {
                    if (attr.getQName().equals("field")) from.setField(attr.getValue());
                    else if (attr.getQName().equals("getter")) from.setGetter(attr.getValue());
                    else if (attr.getQName().equals("setter")) from.setSetter(attr.getValue());
                }
            } else if (qName.equals("To")) {
                from = null;
                to = new BindAttr();
                bind.setTo(to);
                for (XmlAttribute attr : element.attributes()) {
                    if (attr.getQName().equals("field")) to.setField(attr.getValue());
                    else if (attr.getQName().equals("getter")) to.setGetter(attr.getValue());
                    else if (attr.getQName().equals("setter")) to.setSetter(attr.getValue());
                }
            } else {
                callError("{From, To}ではないXMLエレメントが定義されています", element);
            }

        }

        depth++;
    }

    private void callError(String msg, XmlElement element) {
        String publicId = element.getPublicId(), systemId = element.getSystemId();
        int lineNumber = element.getLineNumber(), columnNumber = element.getColumnNumber();
        SAXParseException exp = new SAXParseException(
                msg, publicId, systemId, lineNumber, columnNumber);
        parser.error(exp);
    }

    private void callFatalError(String msg, XmlElement element) {
        String publicId = element.getPublicId(), systemId = element.getSystemId();
        int lineNumber = element.getLineNumber(), columnNumber = element.getColumnNumber();
        SAXParseException exp = new SAXParseException(
                msg, publicId, systemId, lineNumber, columnNumber);
        parser.fatalError(exp);
    }

}
