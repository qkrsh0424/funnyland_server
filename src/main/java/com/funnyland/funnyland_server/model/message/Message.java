package com.funnyland.funnyland_server.model.message;

import lombok.Data;

@Data
public class Message {
    private StatusEnum status;
    private String message;
    private Object data;
    private Object page;

    public Message() {
        this.status = StatusEnum.BAD_REQUEST;
        this.data = null;
        this.message = null;
        this.page = null;
    }
}
