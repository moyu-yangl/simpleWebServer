package com.simplewebserver.domain;

import java.util.Map;

public class ServerResponse implements Response {
    private Map<String, String> headers;
    private String res;
    private int code;

    @Override
    public void setHead(String head, String content) {
        headers.put(head, content);
    }

    @Override
    public String toResult() {
        StringBuilder sb = new StringBuilder();
        sb.append("HTTP/1.1 " + code + "\r\n");
        for (String header : headers.keySet()) {
            sb.append(header).append(": ").append(headers.get(header)).append("\r\n");
        }
        sb.append("\r\n");
        if (res != null)
            sb.append(res);
        return sb.toString();
    }

    @Override
    public void setBody(String content, int code) {
        this.res = content;
        this.code = code;
    }

    public void enableJsonResult() {
        headers.put("Content-Type", "application/json;charset=UTF-8");
    }

    public void enableCors() {
        headers.put("Access-Control-Allow-Origin", "*");
        headers.put("Access-Control-Allow-Credentials", "true");
        headers.put("Access-Control-Max-Age", "1800");
        headers.put("Access-Control-Allow-Methods", "GET,POST,PUT,POST,OPTION");
        headers.put("Access-Control-Allow-Headers", "x-requested-with,content-type");
    }

    public static ServerResponseBuilder builder() {
        return new ServerResponseBuilder();
    }

    public static class ServerResponseBuilder {
        private ServerResponse serverResponse;

        public ServerResponseBuilder() {
            serverResponse = new ServerResponse();
        }

        public ServerResponseBuilder setHeaders(Map headers) {
            serverResponse.headers = headers;
            return this;
        }

        public ServerResponse build() {
            return serverResponse;
        }
    }

}
