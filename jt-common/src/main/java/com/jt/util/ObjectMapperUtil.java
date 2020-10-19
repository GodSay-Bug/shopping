package com.jt.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.util.StringUtils;

/**
 * @Author WL
 * @Date 2020-10-13 15:22
 * @Version 1.0
 * 工具api
 */


/**
 * 1.将用户传来的数据转化为json
 * 2.用户传递的json转换为对象
 */

public class ObjectMapperUtil {

    private static final ObjectMapper MAPPER = new ObjectMapper();


    /**
     * 1.将用户传来的数据转化为json
     *
     * @param ob
     * @return
     */
    public static String toJSON(Object ob) {
        if (ob == null) {
            throw new RuntimeException("传递数据为空，请检查");
        }
        try {
            return MAPPER.writeValueAsString(ob);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * 2.json转对象
     *
     * @param json
     * @param target
     * @param <T>
     * @return
     */

    public static <T> T toObject(String json, Class<T> target) { //Object的话，用户每次调用都需要强转
        if (StringUtils.isEmpty(json) || target == null) {
            throw new RuntimeException("参数不能为空");
        }
        try {
            return MAPPER.readValue(json, target);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
