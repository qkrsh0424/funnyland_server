package com.funnyland.funnyland_server.model.product_category.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "product_category")
public class ProductCategoryPureEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="product_category_id")
    private int productCategoryId;

    @Column(name = "product_category_name")
    private String productCategoryName;

    @Column(name = "product_category_priority")
    private int productCategoryPriority;
    
    @Column(name="product_category_deleted")
    private int productCategoryDeleted;
    
}
