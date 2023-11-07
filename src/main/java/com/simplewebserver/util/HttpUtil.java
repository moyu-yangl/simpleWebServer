package com.simplewebserver.util;

import com.simplewebserver.domain.Request;
import com.simplewebserver.domain.Response;
import com.simplewebserver.domain.ServerRequest;
import com.simplewebserver.domain.ServerResponse;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HttpUtil {
    public static final String[] STATIC;
    
    static {
        STATIC = new String[]{".html", ".js", ".css", ".ico"};
    }

    /**
     * 解析Socket中获取到的请求报文，将其封装为Request
     *
     * @param httpContent
     * @return
     */
    public static Request buildRequest(String httpContent) {
        String[] content = httpContent.split("\r\n");   // 请求报文
        String[] oneHeader = content[0].split(" ");     // 报文首
        String method = oneHeader[0];           // 请求方式
        String path = oneHeader[1];             // 请求路径与参数

        List<String> headers = Arrays.asList(content).subList(1, content.length);
        // 获取请求头
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
        for (String end : STATIC) {
            if (path.endsWith(end)) {
                params.put("static", path);
                return ServerRequest.builder().setHeaders(requestHeader)
                        .setParams(params).build();
            }
        }
        /*
        动态请求
         */
        int i = path.indexOf("?");
        if (i != -1) {
            String p = path.substring(0, i);
            requestHeader.put("path", p);
            String paramList = path.substring(i + 1);
            String[] paramKV = paramList.split("&");
            for (String KV : paramKV) {
                String[] var1 = KV.split("=");
                params.put(var1[0], var1[1]);
            }
        } else {
            requestHeader.put("path", path);
        }
        ServerRequest request = ServerRequest.builder().setHeaders(requestHeader)
                .setParams(params).build();
        return request;
    }

    public static String response2String(Response response) {
        return response.toResult();
    }

    /**
     * 构建静态资源的响应报文Response
     *
     * @param request
     * @param response
     * @throws IOException
     */
    public static void buildStaticResponse(Request request, Response response) throws IOException {
        String path = request.getParam("static");
        File file = null;
        if (path == null) {
            path = "/404.html";
        }
        file = new File("src/main/resources" + path);
        boolean flag = true;
        if (!file.exists()) {
            flag = false;
            file = new File("src/main/resources" + "/404.html");
        }
        BufferedReader reader = new BufferedReader(new FileReader(file));
        StringBuffer sb = new StringBuffer();
        String line = null;
        while ((line = reader.readLine()) != null) {
            sb.append(line).append("\r\n");
        }
        if (flag) {
            response.setHead("Content-Language", "zh-CN");
            response.setHead("Content-Type", "text/html;charset=UTF-8");
            response.setHead("Content-Length", String.valueOf(file.length()));
            response.setBody(sb.toString(), 200);
        } else {
            response.setBody(sb.toString(), 404);
        }
    }

    /**
     * 构建HTTP请求的响应报文
     *
     * @param request
     * @return
     */
    public static Response buildResponse(Request request) {
        Map<String, String> headers = new HashMap<>();
        ServerResponse response = ServerResponse.builder().setHeaders(headers).build();
        return response;
    }

    /**
     * 构建错误请求的响应
     * 使用于服务端发生错误时，会构建一个错误码为500的响应
     *
     * @return
     */
    public static String errorRequest() {
        Map<String, String> map = new HashMap<>();
        ServerResponse response = ServerResponse.builder().setHeaders(map).build();
        response.setHead("Content-Language", "zh-CN");
//        response.setHead("Content-Type", "text/html;charset=UTF-8");
        response.setBody(null, 500);
        return response.toResult();
    }

    /**
     * 执行动态资源请求
     * 根据获取到的映射，通过反射拿到doGet方法，传递request和response进行执行
     *
     * @param o
     * @param request
     * @param response
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    public static void execute(Object o, Request request, Response response) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method[] declaredMethods = o.getClass().getDeclaredMethods();
        for (Method method : declaredMethods) {
            if ("doGet".equals(method.getName())) {
                method.invoke(o, request, response);
                return;
            }
        }
    }
}
