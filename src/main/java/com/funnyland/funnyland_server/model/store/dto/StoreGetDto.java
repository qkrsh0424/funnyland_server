package com.funnyland.funnyland_server.model.store.dto;

import java.util.Date;

import lombok.Data;

@Data
public class StoreGetDto {
    private int storeId;
    private String storeArea;
    private String storeName;
    private String storeAddress;
    private String storePhone;
    private String storeDesc;
    private String storeImageUrl;
    private String storeLat;
    private String storeLng;
    private Date storeCreated;
    private Date storeUpdated;
}
