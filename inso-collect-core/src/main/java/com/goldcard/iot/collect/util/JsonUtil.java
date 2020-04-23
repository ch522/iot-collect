package com.goldcard.iot.collect.util;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author G002005
 * @Date 2020/4/23 9:30
 */
public class JsonUtil {

    public static Map<String, Object> stream2Map(InputStream in) {
        Map<String, Object> result = null;
        try {
            ObjectMapper mapper = new ObjectMapper();
            result = mapper.readValue(in, HashMap.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    public static String obj2Str(Object obj) {
        String str = "";
        try {
            ObjectMapper mapper = new ObjectMapper();
            str = mapper.writeValueAsString(obj);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return str;
    }


}
