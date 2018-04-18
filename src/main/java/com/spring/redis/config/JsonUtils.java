package com.spring.redis.config;

import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.boot.configurationprocessor.json.JSONException;

public class JsonUtils {
    private static final ObjectMapper mapper = new ObjectMapper();
    private static final ObjectMapper simple_mapper = new ObjectMapper();

    public JsonUtils() {
    }

    public static String toJson(Object o) {
        try {
            String r = mapper.writeValueAsString(o);
            return r;
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    public static <T> T fromJson(String src, Class<T> t) {
        try {
            return mapper.readValue(src, t);
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    public static String toSimpleJson(Object o) {
        try {
            String r = simple_mapper.writeValueAsString(o);
            return r;
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    public static <T> T fromSimpleJson(String src, Class<T> t) {
        try {
            return simple_mapper.readValue(src, t);
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    static {
        mapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        mapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        simple_mapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }
}
