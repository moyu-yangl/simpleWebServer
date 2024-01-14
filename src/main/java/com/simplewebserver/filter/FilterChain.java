package com.simplewebserver.filter;


import com.simplewebserver.domain.Request;
import com.simplewebserver.domain.Response;

/**
 * @author sinjinsong
 * @date 2018/5/2
 * 拦截器链
 */
public interface FilterChain {
    /**
     * 当前filter放行，由后续的filter继续进行过滤
     *
     * @param request0
     * @param response0
     */
    void doFilter(Request request, Response response);
}
