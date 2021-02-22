package com.funnyland.funnyland_server.model.page;

import lombok.Data;

@Data
public class PageDto {
    private int itemSize;
    private int displaySize;
    private int pageSize;
    private int curr;
    private int prev;
    private int next;
}
