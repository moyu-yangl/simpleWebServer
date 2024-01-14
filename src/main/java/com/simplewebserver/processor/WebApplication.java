package com.simplewebserver.processor;

import com.simplewebserver.network.handler.ServletContext;

/**
 * 静态持有servletContext，保持servletContext能在项目启动时就被初始化
 */
public class WebApplication {
    private static ServletContext servletContext;

    static {
        servletContext = new ServletContext(null);
    }

    public static ServletContext getServletContext() {
        return servletContext;
    }
}
