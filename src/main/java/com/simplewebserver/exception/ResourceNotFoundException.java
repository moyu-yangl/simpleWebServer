package com.simplewebserver.exception;


import com.simplewebserver.domain.HttpStatus;
import com.simplewebserver.exception.base.ServletException;

/**
 * Created by SinjinSong on 2017/7/21.
 * 静态资源未找到
 */
public class ResourceNotFoundException extends ServletException {
    private static final HttpStatus status = HttpStatus.NOT_FOUND;

    public ResourceNotFoundException() {
        super(status);
    }
}
