package com.simplewebserver.server;

import com.simplewebserver.annotation.ServerAnnotation;
import com.simplewebserver.domain.Request;
import com.simplewebserver.domain.Response;
import com.simplewebserver.domain.ServerResponse;
import com.simplewebserver.domain.SimpleServer;
import com.simplewebserver.test.Test;
import com.simplewebserver.util.JsonUtil;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.UUID;

@ServerAnnotation("/user")
public class TestServer implements SimpleServer {

    public static void main(String[] args) {
//        System.out.println(8 * 8 * 8 * 8 * 4 % 143);
        BigDecimal bigDecimal = new BigDecimal("476512");
        BigDecimal dd = new BigDecimal("476512");

        for (int i = 0; i < 984; i++) {
            bigDecimal = bigDecimal.multiply(dd);
        }
        System.out.println(bigDecimal);
        long k = 1;
        long d = 83;
        for (int i = 0; i < 47; i++) {
            k *= d % 143;
            k = k % 143;
        }
        System.out.println(k % 143);
    }

    @Override
    public void doGet(Request request, Response response) {
        String userid = request.getParam("userid");
        String password = request.getParam("password");
        Test.User test = new Test.User("test", 18);
        ServerResponse response1 = (ServerResponse) response;
        response1.enableJsonResult();
        HashMap<String, String> token = new HashMap<>();
        String t = UUID.randomUUID().toString().replaceAll("-", "");
        token.put("token", t);
        HashMap<String, Object> res = new HashMap<>();
        res.put("code", 200);
        res.put("message", "请求成功");
        res.put("data", token);
        response.setBody(JsonUtil.toJSON(res), 200);
    }

}
