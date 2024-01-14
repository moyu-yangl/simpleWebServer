package com.simplewebserver.exception.base;

import com.simplewebserver.domain.HttpStatus;
import lombok.Getter;

/**
 * Created by SinjinSong on 2017/7/20.
 * 根异常
 */
@Getter
public class ServletException extends Exception {
    private HttpStatus status;

    public ServletException(HttpStatus status) {
        this.status = status;
    }
}
