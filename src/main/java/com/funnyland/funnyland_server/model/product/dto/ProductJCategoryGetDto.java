package com.funnyland.funnyland_server.model.product.dto;

import com.funnyland.funnyland_server.model.product_category.dto.ProductCategoryGetDto;

import lombok.Data;

@Data
public class ProductJCategoryGetDto {
    private ProductGetDto product;
    private ProductCategoryGetDto category;
}
