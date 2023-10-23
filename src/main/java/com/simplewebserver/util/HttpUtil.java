package com.simplewebserver.util;

import com.simplewebserver.domain.Request;
import com.simplewebserver.domain.ServerRequest;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HttpUtil {
    public static final String[] STATIC;

    static {
        STATIC = new String[]{".html", ".js", ".css"};
    }

    public static Request requestBuild(String httpContent) {
        String[] strings = httpContent.split("\r\n");
        String path = strings[0].split(" ")[1];
        List<String> heads = Arrays.asList(strings).subList(1, strings.length);
        Map<String, String> map = new HashMap<>(heads.size());
        for (String head : heads) {
            int m = head.indexOf(":");
            String headKey = head.substring(0, m);
            String headContent = head.substring(m + 2);
            map.put(headKey, headContent);
        }
        Map<String, Object> params = new HashMap<>();
        params.put("path", path);
        ServerRequest request = ServerRequest.builder().setHeaders(map)
                .setParams(params).build();
        return request;
    }
}
