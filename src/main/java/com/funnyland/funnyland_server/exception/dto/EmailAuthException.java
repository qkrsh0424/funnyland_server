package com.funnyland.funnyland_server.exception.dto;

public class EmailAuthException extends RuntimeException {
    public EmailAuthException() {
    }

    public EmailAuthException(String message) {
        super(message);
    }

    public EmailAuthException(String message, Throwable cause) {
        super(message, cause);
    }

    public EmailAuthException(Throwable cause) {
        super(cause);
    }

    public EmailAuthException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
