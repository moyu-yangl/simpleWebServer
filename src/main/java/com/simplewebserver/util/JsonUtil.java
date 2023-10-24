package com.simplewebserver.util;

import com.google.gson.Gson;

public class JsonUtil {

    private static final Gson GSON;

    static {
        GSON = new Gson();
    }

    public static String toJSON(Object o) {
        return GSON.toJson(o);
    }
}
