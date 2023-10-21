package com.simplewebserver.domain;

public interface Response {

    void setHead(String head, String content);

    void setBody(String content);
}
