package com.simplewebserver.network.dispatcher.nio;

import com.simplewebserver.ServerBoot;
import com.simplewebserver.domain.HttpStatus;
import com.simplewebserver.domain.Request;
import com.simplewebserver.domain.Response;
import com.simplewebserver.domain.Servlet;
import com.simplewebserver.exception.ServerErrorException;
import com.simplewebserver.exception.base.ServletException;
import com.simplewebserver.network.dispatcher.AbstractDispatcher;
import com.simplewebserver.network.handler.ServletContext;
import com.simplewebserver.network.handler.nio.NioRequestHandler;
import com.simplewebserver.network.wrapper.SocketWrapper;
import com.simplewebserver.network.wrapper.nio.NioSocketWrapper;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;


@Data
@Slf4j
public class NioDispatcher extends AbstractDispatcher {
    /**
     * 分发请求，注意IO读取必须放在IO线程中进行，不能放到线程池中，否则会出现多个线程同时读同一个socket数据的情况
     * 1、读取数据
     * 2、构造request，response
     * 3、将业务放入到线程池中处理
     *
     * @param socketWrapper
     */
    @Override
    public void doDispatch(SocketWrapper socketWrapper) {
        NioSocketWrapper nioSocketWrapper = (NioSocketWrapper) socketWrapper;
        log.info("已经将请求放入worker线程池中");
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        log.info("开始读取Request");
        Request request = null;
        Response response = null;
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            while (nioSocketWrapper.getSocketChannel().read(buffer) > 0) {
                buffer.flip();
                baos.write(buffer.array());
            }
            baos.close();
            request = new Request(baos.toByteArray());
            response = new Response();
            String url = request.getUrl();
            Servlet servlet = ServerBoot.handlerMapping(ServerBoot.classMap, url);
            ServletContext servletContext1 = new ServletContext(servlet);
            pool.execute(new NioRequestHandler(nioSocketWrapper, servletContext1, exceptionHandler, resourceHandler, request, response));
        } catch (IOException e) {
            e.printStackTrace();
            exceptionHandler.handle(new ServerErrorException(), response, nioSocketWrapper);
        } catch (ServletException e) {
            exceptionHandler.handle(e, response, nioSocketWrapper);
        } catch (InstantiationException | IllegalAccessException e) {
            exceptionHandler.handle(new ServletException(HttpStatus.BAD_REQUEST), response, nioSocketWrapper);
        }
    }
}
