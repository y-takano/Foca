package jp.gr.java_conf.ke.foca.internal;

import java.net.URL;

import jp.gr.java_conf.ke.foca.DIContents;

/**
 * Created by YT on 2017/04/27.
 */

public class InjectRequest {

    private final URL sourceURL;

    private final DIContents contents;

    private final InjectState state;

    public InjectRequest(URL sourceURL, DIContents contents, Object target) {
        this.sourceURL = sourceURL;
        this.contents = contents;
        this.state = new InjectState(contents.hashCode(), target);
    }

    public InjectRequest(InjectRequest request, Object target) {
        this.sourceURL = request.getSourceURL();
        this.contents = request.getContents();
        this.state = request.getState();
        state.setNext(target);
    }

    public InjectState getState() {
        return state;
    }

    public DIContents getContents() {
        return contents;
    }

    public URL getSourceURL() {
        return sourceURL;
    }
}
