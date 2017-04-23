package jp.gr.java_conf.ke.foca;

import org.junit.Test;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

import jp.gr.java_conf.ke.foca.annotation.Controller;
import jp.gr.java_conf.ke.foca.annotation.Driver;
import jp.gr.java_conf.ke.foca.annotation.EntryPoint;
import jp.gr.java_conf.ke.foca.annotation.Gateway;
import jp.gr.java_conf.ke.foca.annotation.InputPort;
import jp.gr.java_conf.ke.foca.annotation.Log;
import jp.gr.java_conf.ke.foca.annotation.Presenter;
import jp.gr.java_conf.ke.foca.annotation.View;
import jp.gr.java_conf.ke.foca.adapter.InterfaceAdapter;
import jp.gr.java_conf.ke.foca.aop.Logger;
import jp.gr.java_conf.ke.foca.xml.FocaXmlSchema;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {

    private static final SimpleDateFormat SDF = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss:SSS z: [  -  ]: ");
    private static final URL URL;
    static {
        URL tmp;
        try {
            tmp = new URL("https://raw.githubusercontent.com/y-takano/Foca/master/foca-dicon_sample.xml");
        } catch(MalformedURLException e) {
            tmp = null;
        }
        URL = tmp;
    }

    private static void sysout(String msg) {
        System.out.println(SDF.format(new Date()) + msg);
    }

    @Test
    public void test1() throws Throwable {
        sysout("test start.");
        sysout("start download dicon-xml. from " + URL);
        Foca foca = Foca.updateDefault(URL);
        sysout("finish xml parse and update di-container.");
        Logger log = foca.getDefaultLogger();
        sysout("DefaultLogger Level:" + log.getLevel());
        TestView target = new TestView();
        log.debug("before inject.");
        foca.inject(target);
        log.debug("after inject.");
        target.execute();
        Thread.sleep(2);
        sysout("test end.");
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
        private InterfaceAdapter controller;

        public void execute() throws Throwable {
            logger.debug("MAIN: execute start.");
            Data dto = new Data();
            dto.set("Hello ");
            controller.invoke(dto);
            logger.debug("MAIN: execute end.");
        }

        @View
        public void print(Data dto) throws Throwable {
            logger.info("VIEW: " + dto.get() + " World!");
        }
    }

    public static class TestUsecase {

        @Log
        private Logger logger;

        @Gateway(name="test-db")
        private InterfaceAdapter database;

        @Presenter(name="test")
        private InterfaceAdapter presenter;

        @InputPort
        public void execute(Data param) throws Throwable {
            param.set(param.get() + "Foca");
            logger.debug("USECASE: request db commit.");
            database.invoke(param);
            logger.debug("USECASE: committed database.");
            presenter.invoke(param);
        }
    }

    public static class TestDBGatewayStub {

        @Log
        private Logger logger;

        @EntryPoint
        public void entrypoint() {
            logger.debug("ENTRY POINT: called.");
        }

        @Driver
        public void execute(Data param) throws Throwable {
            logger.debug("GATEWAY: db commit: " + param.get());
        }
    }
}