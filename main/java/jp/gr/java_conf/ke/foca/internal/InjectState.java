package jp.gr.java_conf.ke.foca.internal;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by YT on 2017/04/27.
 */

public class InjectState {

    private static final Map<Integer, Set<String>> entryCache = new HashMap<Integer, Set<String>>();
    private final Integer fwId;
    private Deque<InjectionOrder> injectChain = new ArrayDeque<InjectionOrder>();
    private Deque<Object> targetChain = new ArrayDeque<Object>();

    public InjectState(Integer fwId, Object target) {
        this.fwId = fwId;
        setNext(target);
    }

    public void setNext(Object target) {
        targetChain.push(target);
    }

    public void complete() {
        targetChain.pop();
    }

    public void done(InjectionOrder done) {
        if (done.equals(lastOrder())) injectChain.pop();
    }

    public Object getTarget() {
        return targetChain.isEmpty() ? null : targetChain.getFirst();
    }

    /**
     * injectorの有効性を取得.
     * @param next
     * @return
     */
    public boolean isInjectable(InjectionOrder next) {
        boolean ret = false;
        switch (next) {
            case Log:
                ret = true;
                break;

            case Controller:
                if (1 < injectChain.size()) return false;
                else if (injectChain.isEmpty()) {
                    ret = true;
                    push(next);
                }
                break;

            case Presenter:
                if (2 < injectChain.size()) return false;
                else if (injectChain.isEmpty()) {
                    ret = true;
                    push(next);
                } else {
                    InjectionOrder last = lastOrder();
                    if (last.equals(InjectionOrder.Controller)
                            || last.equals(InjectionOrder.Gateway)) {
                        ret = true;
                        push(next);
                    }
                }
                break;

            case Gateway:
                if (4 < injectChain.size()) return false;
                else if (injectChain.isEmpty()) {
                    ret = true;
                    push(next);
                } else {
                    InjectionOrder last = lastOrder();
                    if (last.equals(InjectionOrder.Controller)
                            || last.equals(InjectionOrder.Gateway)) {
                        ret = true;
                        push(next);
                    }
                }
                break;

            case Inputport:
                if (3 < injectChain.size()) return false;
                else if (injectChain.isEmpty()) {
                    ret = true;
                    push(next);
                } else {
                    InjectionOrder last = lastOrder();
                    if (last.equals(InjectionOrder.Controller)
                            || last.equals(InjectionOrder.Gateway)) {
                        ret = true;
                    }
                }
                break;

            case View:
                if (2 < injectChain.size()) return false;
                else if (injectChain.isEmpty()) {
                    ret = true;
                    push(next);
                } else {
                    InjectionOrder last = lastOrder();
                    if (last.equals(InjectionOrder.Presenter)) {
                        ret = true;
                    }
                }
                break;

            case Driver:
                if (4 < injectChain.size()) return false;
                else if (injectChain.isEmpty()) {
                    ret = true;
                    push(next);
                } else {
                    InjectionOrder last = lastOrder();
                    if (last.equals(InjectionOrder.Gateway)) {
                        ret = true;
                    }
                }
                break;

            default:
                break;
        }
        return ret;
    }

    public boolean isReadyEntryPoint(String flowName) {
        boolean ret = false;
        Set<String> cache = entryCache.get(fwId);
        if (cache == null) {
            entryCache.put(fwId, new HashSet<String>());
            ret = true;
        } else if (!cache.contains(flowName)) {
            cache.add(flowName);
            ret = true;
        }
        return ret;
    }

    /**
     * method invokeの有効性を取得.
     * @param next
     * @return
     */
    public boolean isCallable(InjectionOrder next) {
        boolean ret = false;
        switch (next) {
            case Inputport:
                if (!injectChain.isEmpty()) {
                    InjectionOrder last = lastOrder();
                    if (last.equals(InjectionOrder.Controller) ||
                            last.equals(InjectionOrder.Gateway)) {
                        ret = true;
                    }
                }
                break;

            case View:
                if (!injectChain.isEmpty()) {
                    InjectionOrder last = lastOrder();
                    if (last.equals(InjectionOrder.Presenter)) {
                        ret = true;
                    }
                }
                break;

            case Driver:
                if (!injectChain.isEmpty()) {
                    InjectionOrder last = lastOrder();
                    if (last.equals(InjectionOrder.Gateway)) {
                        ret = true;
                    }
                }
                break;

            default:
                break;
        }
        return ret;
    }

    private InjectionOrder lastOrder() {
        return injectChain.isEmpty() ? null : injectChain.getFirst();
    }

    private void push(InjectionOrder next) {
        injectChain.push(next);
    }

    public String toString() {
        return new StringBuilder("{")
                .append("target=").append(getTarget())
                .append(", nextProc=").append(lastOrder())
                .append("}").toString();
    }
}
