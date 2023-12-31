package com.simplewebserver.domain;

import java.util.Map;

public class ServerRequest implements Request {
    private Map<String, String> headers;
    private Map<String, String> params;

    public static ServerRequestBuilder builder() {
        return new ServerRequestBuilder();
    }

    @Override
    public String getHead(String head) {
        return headers.get(head);
    }

    @Override
    public String getParam(String param) {
        return params.get(param);
    }

    @Override
    public String getBody(String body) {
        return null;
    }

    @Override
    public boolean isStatic() {
        return params.get("static") != null;
    }

    public static class ServerRequestBuilder {
        private ServerRequest serverRequest;

        public ServerRequestBuilder() {
            serverRequest = new ServerRequest();
        }

        public ServerRequestBuilder setHeaders(Map headers) {
            serverRequest.headers = headers;
            return this;
        }

        public ServerRequestBuilder setParams(Map params) {
            serverRequest.params = params;
            return this;
        }

        public ServerRequest build() {
            return serverRequest;
        }
    }
}
