package com.funnyland.funnyland_server.model.store_area.entity;

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
@Table(name = "store_area")
public class StoreAreaPureEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="store_area_id")
    private int storeAreaId;
    @Column(name="store_area_name")
    private String storeAreaName;
    @Column(name="store_area_created")
    private Date storeAreaCreated;
    @Column(name="store_area_updated")
    private Date storeAreaUpdated;
    @Column(name="store_area_deleted")
    private int storeAreaDeleted;
}
