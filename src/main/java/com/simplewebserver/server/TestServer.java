package com.simplewebserver.server;

import com.simplewebserver.annotation.ServerAnnotation;
import com.simplewebserver.domain.Request;
import com.simplewebserver.domain.Response;
import com.simplewebserver.domain.SimpleServer;
import com.simplewebserver.test.Test;
import com.simplewebserver.util.JsonUtil;

@ServerAnnotation("/user")
public class TestServer implements SimpleServer {

    @Override
    public void doGet(Request request, Response response) {
        String userid = request.getParam("userid");
        String password = request.getParam("password");
        Test.User test = new Test.User("test", 18);
        response.setBody(JsonUtil.toJSON(test), 200);
    }

}
