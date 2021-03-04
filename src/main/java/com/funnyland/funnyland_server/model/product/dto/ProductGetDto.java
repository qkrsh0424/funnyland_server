package com.funnyland.funnyland_server.model.product.dto;

import java.util.Date;

import lombok.Data;

@Data
public class ProductGetDto {
    private int id;
    private int categoryId;
    private int priority;
    private String name;
    private String introduce;
    private String summary;
    private String desc;
    private String imageUrl;
    private boolean newChecked;
    private boolean hitChecked;
    private boolean eventChecked;
    private int hide;
}
