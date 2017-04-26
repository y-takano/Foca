package jp.gr.java_conf.ke.foca;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import jp.gr.java_conf.ke.foca.aop.Logger;
import jp.gr.java_conf.ke.util.Reflection;
import jp.gr.java_conf.ke.namespace.foca.LogLevel;

/**
 * Created by YT on 2017/04/21.
 */

class LoggerThreadService {

    private static final String LOGGER_THREAD_NAME = "Foca-LoggerThread";
    private static final String DEFAULT_LOGGER_NAME = "DEFAULT";
    private static final BlockingQueue<LoggingInterface> queue =
            new ArrayBlockingQueue<LoggingInterface>(1000, true);

    private Thread thread;

    LoggerThreadService() {
        thread = new Thread(new Runnable() {
            public void run() {
                try {
                    while (true) {
                        queue.take().invoke();
                    }
                } catch (InterruptedException e) {
                } catch (Throwable th) {
                    th.printStackTrace();
                    run();
                }
            }
        });
        thread.setDaemon(true);
        thread.setName(LOGGER_THREAD_NAME);
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {}
    }

    Logger createLogger(jp.gr.java_conf.ke.namespace.foca.Logger xmlElement) throws FocaException {
        Logger instance = Reflection.newInstance(xmlElement.getClazz(), Logger.class);
        InvocationHandler handler = new LoggerProxy(xmlElement.getLevel(), instance);
        return (Logger) Proxy.newProxyInstance(
                instance.getClass().getClassLoader(),
                new Class[] {Logger.class},
                handler);
    }

    static String defaultLoggerName() {
        return DEFAULT_LOGGER_NAME;
    }

    private static final class LoggerProxy implements InvocationHandler {

        private LogLevel level;
        private jp.gr.java_conf.ke.foca.aop.Logger logger;

        LoggerProxy(LogLevel level, jp.gr.java_conf.ke.foca.aop.Logger logger) {
            this.level = level;
            this.logger = logger;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            if (method.getName().startsWith("getLevel")) {
                switch (level) {
                    case ERROR:
                        return Logger.Level.ERROR;
                    case INFO:
                        return Logger.Level.INFO;
                    case TRACE:
                        return Logger.Level.TRACE;
                    case DEBUG:
                    default:
                        return Logger.Level.DEBUG;
                }
            }
            switch (level) {
                case ERROR:
                    if (method.getName().equals("info")) return null;
                case INFO:
                    if (method.getName().equals("trace")) return null;
                case TRACE:
                    if (method.getName().equals("debug")) return null;
                case DEBUG:
                default:
                    break;
            }
            LoggingInterface logBean = new LoggingInterface(logger, method, args);
            queue.put(logBean);
            return null;
        }
    }

    private static class LoggingInterface {
        private Object proxy;
        private Method method;
        private Object[] args;

        LoggingInterface(Object proxy, Method method, Object[] args) {
            this.proxy = proxy;
            this.method = method;
            this.args = args;
        }

        void invoke() throws Throwable {
            method.invoke(proxy, args);
        }
    }
}
