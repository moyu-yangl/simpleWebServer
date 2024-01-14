package com.simplewebserver.network.handler;

import com.simplewebserver.connector.Constants;
import com.simplewebserver.domain.HttpStatus;
import com.simplewebserver.domain.Request;
import com.simplewebserver.domain.Response;
import com.simplewebserver.exception.RequestParseException;
import com.simplewebserver.exception.ResourceNotFoundException;
import com.simplewebserver.exception.base.ServletException;
import com.simplewebserver.exception.handler.ExceptionHandler;
import com.simplewebserver.network.wrapper.nio.NioSocketWrapper;
import com.simplewebserver.util.IOUtil;
import com.simplewebserver.util.MimeTypeUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
public class ResourceHandler {
    private ExceptionHandler exceptionHandler;

    public ResourceHandler(ExceptionHandler exceptionHandler) {
        this.exceptionHandler = exceptionHandler;
    }

    public boolean preCheckStaticRequest(Request request) {
        String url = request.getUrl();
        for (String s : Constants.STATIC_RESOURCE) {
            if (url.endsWith(s)) {
                return true;
            }
        }
        return false;
    }

    public void handle(Request request, Response response, NioSocketWrapper socketWrapper) {
        String url = request.getUrl();
        try {
            if (ResourceHandler.class.getResource(url) == null) {
                log.info("找不到该资源:{}", url);
                throw new ResourceNotFoundException();
            }
            byte[] body = IOUtil.getBytesFromFile(url);
            response.setContentType(MimeTypeUtil.getTypes(url));
            response.setBody(body);
        } catch (IOException e) {
            exceptionHandler.handle(new RequestParseException(), response, socketWrapper);
        } catch (ServletException e) {
            exceptionHandler.handle(e, response, socketWrapper);
        } catch (Exception e) {
            exceptionHandler.handle(new ServletException(HttpStatus.NOT_FOUND), response, socketWrapper);
        }
    }
}
