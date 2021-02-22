package com.funnyland.funnyland_server.model.banner.entity;

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
@Table(name = "banner")
public class BannerPureEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "banner_id")
    private Long bannerId;

    @Column(name = "banner_image_url")
    private String bannerImageUrl;

    @Column(name = "banner_order")
    private int bannerOrder;

    @Column(name = "banner_hide")
    private int bannerHide;

    @Column(name = "banner_created")
    private Date bannerCreated;

    @Column(name = "banner_updated")
    private Date bannerUpdated;

    @Column(name = "banner_deleted")
    private int bannerDeleted;
}
