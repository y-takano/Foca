package jp.gr.java_conf.ke.foca.internal;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import jp.gr.java_conf.ke.foca.DIContents;
import jp.gr.java_conf.ke.foca.FocaException;
import jp.gr.java_conf.ke.foca.aop.MethodAdvice;
import jp.gr.java_conf.ke.foca.internal.injector.Injector;
import jp.gr.java_conf.ke.foca.internal.injector.InjectorFactory;
import jp.gr.java_conf.ke.foca.internal.util.AspectWeaver;
import jp.gr.java_conf.ke.foca.internal.util.MethodAdvices;
import jp.gr.java_conf.ke.foca.internal.validator.Annotations;
import jp.gr.java_conf.ke.foca.internal.validator.Validator.EnabledMarker;
import jp.gr.java_conf.ke.namespace.foca.Joinpoint;
import jp.gr.java_conf.ke.util.Reflection;

/**
 * Created by YT on 2017/05/05.
 */

class EntrypointStartService {

    private List<Thread> threadList = new LinkedList<Thread>();

    public void ready(Iterable<EnabledMarker> component,
            InjectRequest request) throws FocaException {

        DIContents contents = request.getContents();
        Joinpoint joinpoint = null;
        String flowName = null;
        for (EnabledMarker marker : component) {
            flowName = Annotations.getFlowName(marker.annotation());
            if (flowName == null) continue;
            joinpoint = contents.selectEntrypoint(flowName);
        }

        // EntryPoint未定義は正常のため何もせず返す。
        if (joinpoint == null) return;

        // EntryPointが2つ以上存在時はXSD設計と矛盾するため内部異常
        final Object target = Reflection.newInstance(joinpoint.getInject().getClazz());
        List<EnabledMarker> entrypoint = Annotations.collect(target.getClass().getDeclaredMethods(),
                InjectionOrder.Entrypoint).validate();
        if (entrypoint.size() < 1 || 1 < entrypoint.size()) {
            throw new InternalError("xsdと処理結果が矛盾。");
        }

        // 起動待機中のエントリポイントでない（起動済）場合は何もしない
        InjectState state = request.getState();
        if (!state.isReadyEntryPoint(flowName)) return;

        // EntryPointへLogを注入
        Collection<EnabledMarker> log = Annotations.collect(
                target.getClass().getDeclaredFields(),
                InjectionOrder.Log).validate();
        if (!log.isEmpty()) {
            // Log注入実行
            if (state.isInjectable(InjectionOrder.Log)) {
                for (EnabledMarker marker : log) {
                    InjectorFactory factory = new InjectorFactory(request);
                    Injector injector = factory.create(marker.annotation());
                    injector.inject(target, marker.field());
                }
            }
        }

        // AspectWeaverを生成
        List<MethodAdvice> advices =
                MethodAdvices.asList(joinpoint, contents.aspecters());
        final AspectWeaver weaver = new AspectWeaver(target, advices);
        final Method method = entrypoint.get(0).method();

        // 別スレッドを起動しAspectWeaverを呼び出す
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    weaver.invoke(method, null);
                } catch (Throwable th) {
                    th.printStackTrace();
                }
            }
        });
        thread.setName("Foca-EntrypointService-Thread");
        thread.setDaemon(true);
        threadList.add(thread);
    }

    public void start() {
        for (Thread th : threadList) th.start();
    }
}
