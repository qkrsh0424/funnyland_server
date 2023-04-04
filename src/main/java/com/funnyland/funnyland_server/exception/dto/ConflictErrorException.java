package com.funnyland.funnyland_server.exception.dto;

/**
 * 409
 */
public class ConflictErrorException extends RuntimeException{
    public ConflictErrorException() {
        super();
    }
    public ConflictErrorException(String message, Throwable cause) {
        super(message, cause);
    }
    public ConflictErrorException(String message) {
        super(message);
    }
    public ConflictErrorException(Throwable cause) {
        super(cause);
    }
}
