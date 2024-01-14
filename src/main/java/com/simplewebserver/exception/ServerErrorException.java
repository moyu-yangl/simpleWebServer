package com.simplewebserver.exception;

import com.simplewebserver.domain.HttpStatus;
import com.simplewebserver.exception.base.ServletException;

public class ServerErrorException extends ServletException {
    private static final HttpStatus status = HttpStatus.NOT_FOUND;

    public ServerErrorException() {
        super(status);
    }
}
