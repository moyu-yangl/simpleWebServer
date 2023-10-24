package com.simplewebserver.server;

import com.simplewebserver.annotation.ServerAnnotation;
import com.simplewebserver.domain.Request;
import com.simplewebserver.domain.Response;
import com.simplewebserver.domain.ServerResponse;
import com.simplewebserver.domain.SimpleServer;
import com.simplewebserver.test.Test;
import com.simplewebserver.util.JsonUtil;

import java.util.HashMap;
import java.util.UUID;

@ServerAnnotation("/user")
public class TestServer implements SimpleServer {

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
