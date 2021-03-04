package com.funnyland.funnyland_server.model.store.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name = "store")
public class StorePureEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="store_id")
    private int storeId;
    @Column(name="store_area")
    private String storeArea;
    @Column(name="store_name")
    private String storeName;
    @Column(name="store_address")
    private String storeAddress;
    @Column(name="store_phone")
    private String storePhone;
    @Column(name="store_desc")
    private String storeDesc;
    @Column(name="store_image_url")
    private String storeImageUrl;
    @Column(name="store_lat")
    private String storeLat;
    @Column(name="store_lng")
    private String storeLng;
    @Column(name="store_created")
    private Date storeCreated;
    @Column(name="store_updated")
    private Date storeUpdated;
    @Column(name="store_deleted")
    private int storeDeleted;
}
