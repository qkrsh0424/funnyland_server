package com.funnyland.funnyland_server.model.product.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "product")
public class ProductPureEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private int productId;

    @Column(name="product_category_id")
    private int productCategoryId;

    @Column(name="product_priority")
    private int productPriority;

    @Column(name="product_name")
    private String productName;

    @Column(name="product_introduce")
    private String productIntroduce;

    @Column(name="product_summary")
    private String productSummary;

    @Column(name="product_desc")
    private String productDesc;

    @Column(name="product_image_url")
    private String productImageUrl;

    @Column(name="product_hide")
    private int productHide;

    @Column(name="product_created")
    private Date productCreated;

    @Column(name="product_updated")
    private Date productUpdated;

    @Column(name="product_deleted")
    private int productDeleted;
}
