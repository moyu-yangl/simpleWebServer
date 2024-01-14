package com.simplewebserver.exception.handler;


import com.simplewebserver.domain.Header;
import com.simplewebserver.domain.Response;
import com.simplewebserver.exception.RequestInvalidException;
import com.simplewebserver.exception.base.ServletException;
import com.simplewebserver.network.wrapper.SocketWrapper;
import com.simplewebserver.util.IOUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;


/**
 * Created by SinjinSong on 2017/7/20.
 * 异常处理器
 * 会根据异常对应的HTTP Status设置response的状态以及相应的错误页面
 */
@Slf4j
public class ExceptionHandler {

    public void handle(ServletException e, Response response, SocketWrapper socketWrapper) {
        try {
            if (e instanceof RequestInvalidException) {
                log.info("请求无法读取，丢弃");
                socketWrapper.close();
            } else {
                log.info("抛出异常:{}", e.getClass().getName());
                e.printStackTrace();
                response.addHeader(new Header("Connection", "close"));
                response.setStatus(e.getStatus());
                response.setBody(IOUtil.getBytesFromFile(
                        String.format("/errors/%s.html", String.valueOf(e.getStatus().getCode()))));
            }
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }
}
