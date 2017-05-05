package jp.gr.java_conf.ke.foca.converter;

import jp.gr.java_conf.ke.util.Reflection;

/**
 * Created by YT on 2017/04/12.
 */

public class DefaultFactory<DTO> implements OutModelFactory<DTO> {

    @Override
    public DTO createParameter(String outClass) throws Exception {
        return Reflection.newInstance(outClass);
    }
}
