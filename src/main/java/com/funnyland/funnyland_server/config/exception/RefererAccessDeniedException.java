package com.funnyland.funnyland_server.config.exception;

public class RefererAccessDeniedException extends RuntimeException{
    public RefererAccessDeniedException(String msg) {
        super(msg);
    }
    public RefererAccessDeniedException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
