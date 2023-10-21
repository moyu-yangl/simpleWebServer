package com.simplewebserver.domain;

public interface SimpleServer {
    void doGet(Request request, Response response);

    void doPost(Request request, Response response);
}
