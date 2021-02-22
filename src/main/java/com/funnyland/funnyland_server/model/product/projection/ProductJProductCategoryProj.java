package com.funnyland.funnyland_server.model.product.projection;

import com.funnyland.funnyland_server.model.product.entity.ProductPureEntity;
import com.funnyland.funnyland_server.model.product_category.entity.ProductCategoryPureEntity;

public interface ProductJProductCategoryProj {
    ProductPureEntity getProduct();
    ProductCategoryPureEntity getCategory();
}
