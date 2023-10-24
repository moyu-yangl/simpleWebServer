package com.simplewebserver.domain;

public interface Request {

    String getHead(String head);

    String getParam(String param);

    String getBody(String body);

    boolean isStatic();


}
