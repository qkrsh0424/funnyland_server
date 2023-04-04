package com.funnyland.funnyland_server.enums;

public enum DeletedFlagEnums {
    DELETED(true),
    EXIST(false);

    private boolean value;


    DeletedFlagEnums(boolean value) {
        this.value=value;
    }

    public boolean getValue() {
        return value;
    }
}
