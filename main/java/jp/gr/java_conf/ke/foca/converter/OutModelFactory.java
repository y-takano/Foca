package jp.gr.java_conf.ke.foca.converter;

/**
 * Created by YT on 2017/04/12.
 */

public interface OutModelFactory<DTO> {

    DTO createParameter(String className) throws Exception;
}
