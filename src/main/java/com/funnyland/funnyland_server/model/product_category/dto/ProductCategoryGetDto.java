package com.funnyland.funnyland_server.model.product_category.dto;

import lombok.Data;

@Data
public class ProductCategoryGetDto {
    private int id;
    private String categoryName;
    private int priority;
}
