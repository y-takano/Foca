package jp.gr.java_conf.ke.foca.internal.injector;

import java.lang.annotation.Annotation;

/**
 * Created by YT on 2017/04/22.
 */

class NameResolver {

    private static final String MARK_CONTROLLER = "jp.gr.java_conf.ke.foca.annotation.Controller";
    private static final String MARK_PRESENTER = "jp.gr.java_conf.ke.foca.annotation.Presenter";
    private static final String MARK_GATEWAY = "jp.gr.java_conf.ke.foca.annotation.Gateway";
    private static final String MARK_LOGGER = "jp.gr.java_conf.ke.foca.annotation.Log";
//    private static final String MARK_INPUTPORT = "jp.gr.java_conf.ke.foca.annotation.InputPort";
//    private static final String MARK_VIEW = "jp.gr.java_conf.ke.foca.annotation.View";
//    private static final String MARK_DRIVER = "jp.gr.java_conf.ke.foca.annotation.Driver";

    String lookupControllerMarker(Annotation anno) {
        if (anno.annotationType().getCanonicalName().equals(MARK_CONTROLLER)) {
            return ((jp.gr.java_conf.ke.foca.annotation.Controller)anno).name();
        }
        return null;
    }

    String lookupPresenterMarker(Annotation anno) {
        if (anno.annotationType().getCanonicalName().equals(MARK_PRESENTER)) {
            return ((jp.gr.java_conf.ke.foca.annotation.Presenter)anno).name();
        }
        return null;
    }

    String lookupGatewayMarker(Annotation anno) {
        if (anno.annotationType().getCanonicalName().equals(MARK_GATEWAY)) {
            return ((jp.gr.java_conf.ke.foca.annotation.Gateway)anno).name();
        }
        return null;
    }

    String lookupLogMarker(Annotation anno) {
        if (anno.annotationType().getCanonicalName().equals(MARK_LOGGER)) {
            return ((jp.gr.java_conf.ke.foca.annotation.Log)anno).name();
        }
        return null;
    }

//    boolean lookupInputPortMarker(Annotation anno) {
//        if (anno.annotationType().getCanonicalName().equals(MARK_INPUTPORT)) {
//            return true;
//        }
//        return false;
//    }
//
//    boolean lookupViewMarker(Annotation anno) {
//        if (anno.annotationType().getCanonicalName().equals(MARK_VIEW)) {
//            return true;
//        }
//        return false;
//    }
//
//    boolean lookupDriverMarker(Annotation anno) {
//        if (anno.annotationType().getCanonicalName().equals(MARK_DRIVER)) {
//            return true;
//        }
//        return false;
//    }
}
