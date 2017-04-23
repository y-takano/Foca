package jp.gr.java_conf.ke.foca.internal.injector;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayDeque;
import java.util.Deque;

import jp.gr.java_conf.ke.foca.DIContents;
import jp.gr.java_conf.ke.foca.FocaException;
import jp.gr.java_conf.ke.foca.XmlConsistencyException;
import jp.gr.java_conf.ke.foca.adapter.InterfaceAdapter;

/**
 * Created by YT on 2017/04/22.
 */

public class InjectorFactory {

    enum InjectType {
        Controller,
        Presenter,
        Gateway,
        Log,
    }

    private DIContents contents;
    private NameResolver resolver;

    public InjectorFactory(DIContents contents) {
        this.contents = contents;
        this.resolver = new NameResolver();
    }

    public Injector create(Annotation anno) throws FocaException {
        boolean existController, existPresenter, existGateway, existLog;
        String tmpName1, tmpName2, tmpName3, tmpName4;
        existController = (tmpName1 = resolver.lookupControllerMarker(anno)) != null;
        existPresenter = (tmpName2 = resolver.lookupPresenterMarker(anno)) != null;
        existGateway = (tmpName3 = resolver.lookupGatewayMarker(anno)) != null;
        existLog = (tmpName4 = resolver.lookupLogMarker(anno)) != null;

        String adapterName;
        if (existController) adapterName = tmpName1;
        else if (existPresenter) adapterName = tmpName2;
        else if (existGateway) adapterName = tmpName3;
        else if (existLog) adapterName = tmpName4;
        else adapterName = null;

        int cnt = existController ? 1 : 0;
        cnt = existPresenter ? cnt+1 : cnt;
        cnt = existGateway ? cnt+1 : cnt;
        cnt = existLog ? cnt+1 : cnt;
        if (cnt == 0) return new DummyInjector();

        boolean overlap = cnt > 1;
        if (overlap) {
            String ctrl = existController ? " @Controller " : "";
            String prst = existPresenter ? " @Presenter " : "";
            String gtwy = existGateway ? " @Gateway " : "";
            String log = existLog ? " @Log " : "";
            throw new XmlConsistencyException(
                    "重複マーカー:" + ctrl + prst + gtwy + log);
        }

        InjectType type = null;
        if (existController) type = InjectType.Controller;
        else if (existPresenter) type = InjectType.Presenter;
        else if (existGateway) type = InjectType.Gateway;
        else if (existLog) type = InjectType.Log;
        return createInjector(type, adapterName);
    }

    private static final Deque<InjectType> injectStack = new ArrayDeque<InjectType>();

    private Injector createInjector(InjectType order, String adapterName) {
        if (order == null) throw new InternalError();
        Injector injector;
        InjectType last = injectStack.peekLast();
        switch(order) {
            case Controller:
                if (last == null) {
                    injector = new ControllerInjector(contents, adapterName);
                    injectStack.push(order);
                } else {
                    injector = new DummyInjector();
                }
                break;
            case Presenter:
                if (last == null || !last.equals(InjectType.Controller)) {
                    injector = new DummyInjector();
                } else {
                    injector = new PresenterInjector(contents, adapterName);
                    injectStack.push(order);
                }
                break;
            case Gateway:
                if (1 < injectStack.size()) {
                    injector = new DummyInjector();
                } else if (last == null || last.equals(InjectType.Controller) || last.equals(InjectType.Gateway)) {
                    injector = new GatewayInjector(contents, adapterName);
                    injectStack.push(order);
                } else {
                    injector = new DummyInjector();
                }
                break;
            case Log:
                injector = new LoggerInjector(contents, adapterName);
                break;
            default:
                throw new InternalError();
        }
        return injector;
    }

    private static class DummyInjector extends Injector {

        private DummyInjector() {
            super(null, null);
        }

        @Override
        InterfaceAdapter<?, ?> getAdapter(DIContents containts, String adapterName) throws FocaException {
            return null;
        }

        public void inject(Object injectee, Field targetField) {}
    }
}
