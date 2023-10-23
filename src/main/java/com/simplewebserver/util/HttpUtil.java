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
        String[] split = strings[0].split(" ");
        String method = split[0];
        String path = split[1];
        List<String> heads = Arrays.asList(strings).subList(1, strings.length);
        Map<String, String> map = new HashMap<>(heads.size());
        for (String head : heads) {
            String[] head2 = head.split(": ");
            if (head2.length >= 2) {
                String headKey = head2[0];
                String headContent = head2[1];
                map.put(headKey, headContent);
            }
        }
        map.put("method", method);
        Map<String, Object> params = new HashMap<>();
        boolean jump = false;
        for (String end : STATIC) {
            if (path.endsWith(end)) {
                map.put("path", path);
                jump = true;
                break;
            }
        }

        if (!jump) {
            int i = path.indexOf("?");
            String p = path.substring(0, i);
            map.put("path", p);
            String param = path.substring(i + 1);
            String[] split1 = param.split("&");
            for (String s : split1) {
                String[] split2 = s.split("=");
                params.put(split2[0], split2[1]);
            }
        }

        ServerRequest request = ServerRequest.builder().setHeaders(map)
                .setParams(params).build();
        return request;
    }

    public static String buildResponseStatic(String content, long len, int code, String message) {
        StringBuffer result = new StringBuffer();
        result.append("HTTP/1.1 " + code + " " + message + "\r\n");
        result.append("Content-Language: zh-CN \r\n");
        result.append("Content-Type: text/html;charset=UTF-8 \r\n");
        result.append("Content-Length: " + len + "\r\n");
        result.append("\r\n" + content);
        return result.toString();
    }

}
