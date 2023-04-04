package com.funnyland.funnyland_server.exception.dto;

/**
 * Http Status Code : 400
 */
public class UnsatisfiedParametersException extends RuntimeException{
    public UnsatisfiedParametersException() {
        super();
    }
    public UnsatisfiedParametersException(String message, Throwable cause) {
        super(message, cause);
    }
    public UnsatisfiedParametersException(String message) {
        super(message);
    }
    public UnsatisfiedParametersException(Throwable cause) {
        super(cause);
    }
}
