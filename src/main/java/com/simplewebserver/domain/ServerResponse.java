package com.simplewebserver.domain;

import java.util.Map;

public class ServerResponse implements Response {
    private Map<String, String> headers;
    private String res;
    private int code;
    private String message;

    @Override
    public void setHead(String head, String content) {
        headers.put(head, content);
    }

    @Override
    public String toResult() {
        StringBuilder sb = new StringBuilder();
        sb.append("HTTP/1.1 " + code + " " + message + "\r\n");
        for (String header : headers.keySet()) {
            sb.append(header).append(": ").append(headers.get(header)).append("\r\n");
        }
        sb.append("\r\n");
        sb.append(res);
        return sb.toString();
    }

    @Override
    public void setBody(String content, int code, String message) {
        this.res = content;
        this.code = code;
        this.message = message;
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
