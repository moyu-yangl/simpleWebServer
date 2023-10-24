package com.simplewebserver.domain;

import java.util.Map;

public class ServerResponse implements Response {
    private Map<String, String> headers;

    @Override
    public void setHead(String head, String content) {

    }

    @Override
    public String toResult() {
        return null;
    }

    @Override
    public void setBody(String content) {

    }
}
