package jp.gr.java_conf.ke.foca.internal;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Created by YT on 2017/04/27.
 */

public class InjectState {

    private Deque<InjectionOrder> injectChain = new ArrayDeque<InjectionOrder>();

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
                    injectChain.push(next);
                }
                break;

            case Presenter:
                if (2 < injectChain.size()) return false;
                else if (!injectChain.isEmpty()) {
                    InjectionOrder last = injectChain.getFirst();
                    if (last.equals(InjectionOrder.Controller)
                            || last.equals(InjectionOrder.Gateway)) {
                        ret = true;
                        injectChain.push(next);
                    }
                }
                break;

            case Gateway:
                if (4 < injectChain.size()) return false;
                else if (injectChain.isEmpty()) {
                    ret = true;
                    injectChain.push(next);
                } else {
                    InjectionOrder last = injectChain.getFirst();
                    if (last.equals(InjectionOrder.Controller)
                            || last.equals(InjectionOrder.Gateway)) {
                        ret = true;
                        injectChain.push(next);
                    }
                }
                break;

            case Inputport:
                if (3 < injectChain.size()) return false;
                else if (!injectChain.isEmpty()) {
                    InjectionOrder last = injectChain.getLast();
                    if (last.equals(InjectionOrder.Controller)
                            || last.equals(InjectionOrder.Gateway)) {
                        ret = true;
                    }
                }
                break;

            case View:
                if (2 < injectChain.size()) return false;
                else if (!injectChain.isEmpty()) {
                    InjectionOrder last = injectChain.getFirst();
                    if (last.equals(InjectionOrder.Presenter)) {
                        ret = true;
                    }
                }
                break;

            case Driver:
                if (4 < injectChain.size()) return false;
                else if (!injectChain.isEmpty()) {
                    InjectionOrder last = injectChain.getFirst();
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

    /**
     * method invokeの有効性を取得.
     * @param next
     * @return
     */
    public boolean isCallable(InjectionOrder next) {
        boolean ret = false;
        switch (next) {
            case Entrypoint:
                if (injectChain.isEmpty()) {
                    ret = true;
                }
                break;

            case Inputport:
                if (!injectChain.isEmpty()) {
                    InjectionOrder last = injectChain.getFirst();
                    if (last.equals(InjectionOrder.Controller) ||
                            last.equals(InjectionOrder.Gateway)) {
                        ret = true;
                    }
                }
                break;

            case View:
                if (!injectChain.isEmpty()) {
                    InjectionOrder last = injectChain.getFirst();
                    if (last.equals(InjectionOrder.Presenter)) {
                        ret = true;
                    }
                }
                break;

            case Driver:
                if (!injectChain.isEmpty()) {
                    InjectionOrder last = injectChain.getFirst();
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

}
