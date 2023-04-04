package com.funnyland.funnyland_server.exception.dto;

/**
 * Http Status code : 403
 */
public class SubscriptionPlanAccessDeniedException extends RuntimeException{
    public SubscriptionPlanAccessDeniedException() {
        super();
    }
    public SubscriptionPlanAccessDeniedException(String message, Throwable cause) {
        super(message, cause);
    }
    public SubscriptionPlanAccessDeniedException(String message) {
        super(message);
    }
    public SubscriptionPlanAccessDeniedException(Throwable cause) {
        super(cause);
    }
}
