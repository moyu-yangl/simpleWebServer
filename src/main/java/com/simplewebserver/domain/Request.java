package com.simplewebserver.domain;

public interface Request {

    String getHead(String head);

    Object getParam(String param);

    String getBody(String body);


}
