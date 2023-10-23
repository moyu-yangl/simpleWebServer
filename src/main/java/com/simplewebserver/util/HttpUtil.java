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

    public static Request buildRequest(String httpContent) {
        String[] content = httpContent.split("\r\n");   // 请求报文
        String[] oneHeader = content[0].split(" ");     // 报文首
        String method = oneHeader[0];           // 请求方式
        String path = oneHeader[1];             // 请求路径与参数

        List<String> headers = Arrays.asList(content).subList(1, content.length);

        Map<String, String> requestHeader = new HashMap<>(headers.size());
        for (String head : headers) {
            String[] headerKV = head.split(": ");
            if (headerKV.length >= 2) {
                String headKey = headerKV[0];
                String headContent = headerKV[1];
                requestHeader.put(headKey, headContent);
            }
        }
        requestHeader.put("method", method);
        Map<String, String> params = new HashMap<>();
        /*
        判断是否为静态资源请求
         */
        boolean jump = false;
        for (String end : STATIC) {
            if (path.endsWith(end)) {
                requestHeader.put("path", path);
                jump = true;
                break;
            }
        }
        /*
        动态请求
         */
        if (!jump) {
            int i = path.indexOf("?");
            String p = path.substring(0, i);
            requestHeader.put("path", p);

            String paramList = path.substring(i + 1);
            String[] paramKV = paramList.split("&");
            for (String KV : paramKV) {
                String[] var1 = KV.split("=");
                params.put(var1[0], var1[1]);
            }
        }

        ServerRequest request = ServerRequest.builder().setHeaders(requestHeader)
                .setParams(params).build();
        return request;
    }

    public static String buildStaticResponse(String content, long len, int code, String message) {
        StringBuffer result = new StringBuffer();
        result.append("HTTP/1.1 " + code + " " + message + "\r\n");
        result.append("Content-Language: zh-CN \r\n");
        result.append("Content-Type: text/html;charset=UTF-8 \r\n");
        result.append("Content-Length: " + len + "\r\n");
        result.append("\r\n" + content);
        return result.toString();
    }

}
