package com.tide.utils;

public class JsonUtils {

    /**
     * 获取接送格式字符串
     *
     * @param key
     * @param value
     * @return
     */
    public static String getJson(String key, String value) {
        return "{\"" + key + "\": \"" + value + "\"}";
    }
}
