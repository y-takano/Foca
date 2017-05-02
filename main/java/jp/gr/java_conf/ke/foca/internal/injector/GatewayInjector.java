package jp.gr.java_conf.ke.foca.internal.injector;

import java.util.List;

import jp.gr.java_conf.ke.foca.DIContents;
import jp.gr.java_conf.ke.foca.InjectException;
import jp.gr.java_conf.ke.foca.annotation.Driver;
import jp.gr.java_conf.ke.foca.annotation.InputPort;
import jp.gr.java_conf.ke.foca.DefinitionNotFound;
import jp.gr.java_conf.ke.foca.FocaException;
import jp.gr.java_conf.ke.foca.adapter.InterfaceAdapter;
import jp.gr.java_conf.ke.foca.internal.adapter.AdapterFactory;
import jp.gr.java_conf.ke.namespace.foca.Aspect;
import jp.gr.java_conf.ke.namespace.foca.Converter;
import jp.gr.java_conf.ke.namespace.foca.Gateway;
import jp.gr.java_conf.ke.namespace.foca.Joinpoint;

/**
 * Created by YT on 2017/03/26.
 */

class GatewayInjector extends Injector {

    private static final String NL = System.getProperty("line.separator");

    GatewayInjector(DIContents containts, String adapterName, AdapterFactory factory) {
        super(containts, adapterName, factory);
    }

    @Override
    InterfaceAdapter<?, ?> getAdapter(DIContents contents, String gatewayName)
            throws FocaException {

        List<Gateway> gateways = contents.selectGateway(gatewayName);
        if (gateways == null) {
            throw new DefinitionNotFound(
                    getFieldName(), gatewayName,
                    jp.gr.java_conf.ke.foca.annotation.Gateway.class);
        }

        FocaException exception = null;
        InterfaceAdapter<?, ?> adapter = null;
        Iterable<Aspect> aspects = contents.aspecters();
        Gateway gateway = gateways.get(0);
        try {
            adapter = createAdapter(gateway.getConverter(), gateway, aspects);
        } catch (FocaException e) {
            exception = new InjectException(
                    "フィールドインジェクションに失敗しました。" + NL +
                            "フィールド: " + getFieldName()
                    , e);
        }

        if (adapter != null) {
            return adapter;
        } else if (gateways.size() == 1) {
            throw exception;
        }

        gateway = gateways.get(1);
        try {
            adapter = createAdapter(gateway.getConverter(), gateway, aspects);
        } catch (FocaException e) {
            FocaException newExp = new InjectException(
                    "フィールドインジェクションに失敗しました。" + NL +
                            "フィールド: " + getFieldName()
                    , e);
            if (exception == null) {
                exception = newExp;
            } else {
                exception.setNext(newExp);
            }
            throw exception;
        }

        return adapter;
    }

    private InterfaceAdapter<?, ?> createAdapter(
            Converter cnv, Gateway gateway, Iterable<Aspect> aspects) throws FocaException {

        InterfaceAdapter<?, ?> adapter = null;

        Joinpoint inputPort = gateway.getInputPort();
        if (inputPort != null) {
            adapter = factory().createAdapter(
                    cnv, InputPort.class, inputPort, aspects);
        }

        Joinpoint driver = gateway.getDriver();
        if (driver != null) {
            adapter = factory().createAdapter(
                    cnv, Driver.class, driver, aspects);
        }

        return adapter;
    }
}
