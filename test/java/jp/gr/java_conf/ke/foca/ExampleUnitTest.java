package jp.gr.java_conf.ke.foca;

import org.junit.Test;

import java.net.MalformedURLException;
import java.net.URL;

import jp.gr.java_conf.ke.foca.annotation.entrance.FetchableAdapter;
import jp.gr.java_conf.ke.foca.annotation.entrance.Controller;
import jp.gr.java_conf.ke.foca.annotation.exit.Driver;
import jp.gr.java_conf.ke.foca.annotation.exit.EntryPoint;
import jp.gr.java_conf.ke.foca.annotation.entrance.Gateway;
import jp.gr.java_conf.ke.foca.annotation.exit.InputPort;
import jp.gr.java_conf.ke.foca.annotation.Log;
import jp.gr.java_conf.ke.foca.annotation.entrance.Presenter;
import jp.gr.java_conf.ke.foca.annotation.exit.View;
import jp.gr.java_conf.ke.foca.annotation.entrance.InterfaceAdapter;
import jp.gr.java_conf.ke.foca.annotation.Logger;

/**
 * サンプルXMLを利用した代表機能のスルーテスト
 *
 * @see <a href="https://raw.githubusercontent.com/y-takano/Foca/master/foca-dicon_sample.xml">サンプルXML</a>
 */
public class ExampleUnitTest {

    private static final URL URL;
    static {
        URL tmp;
        try {
            tmp = new URL("https://raw.githubusercontent.com/y-takano/Foca/master/foca-dicon_sample.xml");
            //tmp = new URL("file:///C:\\Users\\YT\\AndroidStudioProjects\\Foca\\app\\src\\main\\assets\\foca-dicon.xml");
        } catch(MalformedURLException e) {
            tmp = null;
        }
        URL = tmp;
    }

    @Test
    public void test1() throws Throwable {
        Logger log = Foca.getFocaDefaultLogger();
        log.info("test start.");
        log.debug("start download dicon-xml. from " + URL);
        Foca foca = Foca.updateDefault(URL);
        log = foca.getDefaultLogger();
        log.debug("finish xml parse and update di-container.");
        log.info("DefaultLogger Level:" + log.getLevel());
        log.debug("before inject.");
        TestView view = foca.createInstance(TestView.class);
        log.debug("after inject.");
        view.repaint();
        Thread.sleep(5);
        log.info("test end.");
    }

    public static class Data {

        private String str;

        public void set(String str) {
            this.str = str;
        }
        public String get() {
            return str;
        }
        public String toString() {
            return get();
        }
    }

    public static class TestView {

        @Log
        private Logger logger;

        @Controller(name="test")
        private InterfaceAdapter<Data, ?> controller;

        public void repaint() throws Throwable {
            logger.debug("[MAIN   ]: start repaint.");
            Data dto = new Data();
            dto.set("Hello ");
            controller.invoke(dto);
            logger.debug("[MAIN   ]: end repaint.");
        }

        @View
        public void print(Data dto) throws Throwable {
            logger.info("[VIEW   ]: " + dto.get() + " World!");
        }
    }

    public static class TestUsecase {

        @Log
        private Logger logger;

        @Gateway(name="test-db")
        private FetchableAdapter<Data, Data, ?> database;

        @Presenter(name="test")
        private InterfaceAdapter<Data, ?> presenter;

        @InputPort
        public void update(Data param) throws Throwable {
            param.set(param.get() + "Foca");
            logger.debug("[USECASE]: select database.");
            Data selected = database.fetch(param);
            logger.debug("[USECASE]: update display.");
            presenter.invoke(selected);
            logger.debug("[USECASE]: complete.");
        }
    }

    public static class TestDBAdapter implements FetchableAdapter<Data, Data, Throwable> {

        @Log
        private Logger logger;

        @Driver
        private FetchableAdapter<Data, Data, ?> database;

        public void invoke(Data data) throws Throwable {
            fetch(data);
        }

        public Data fetch(Data data) throws Throwable {
            return database.fetch(data);
        }
    }

    public static class TestDBGatewayStub {

        @Log
        private Logger logger;

        @EntryPoint
        public void entrypoint() {
            logger.debug("[ENTRYPOINT]: called.");
        }

        @Driver
        public Data select(Data param) throws Throwable {
            logger.debug("[GATEWAY]: select result: " + param);
            return param;
        }
    }
}