package com.simplewebserver.network.handler;

import com.simplewebserver.domain.Servlet;
import lombok.Data;

@Data
public class ServletContext {
    private Servlet servlet;

    public ServletContext(Servlet servlet) {
        this.servlet = servlet;
    }
}
