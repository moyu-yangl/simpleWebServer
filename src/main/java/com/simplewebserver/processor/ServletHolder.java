package com.simplewebserver.processor;

import lombok.Data;

/**
 * @author sinjinsong
 * @date 2018/5/2
 */
@Data
public class ServletHolder {
    private String servlet;
    private String servletClass;

    public ServletHolder(String servletClass) {
        this.servletClass = servletClass;
    }
}
