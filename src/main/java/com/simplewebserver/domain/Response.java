package com.simplewebserver.domain;

public interface Response {

    void setHead(String head, String content);

    String toResult();

    void setBody(String content, int code, String message);
}
