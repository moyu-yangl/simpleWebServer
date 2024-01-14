package com.simplewebserver.domain;

public interface Servlet {
    void doGet(Request request, Response response);
}
