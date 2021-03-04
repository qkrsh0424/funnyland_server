package com.funnyland.funnyland_server.model.store_area.dto;

import java.util.Date;

import lombok.Data;

@Data
public class StoreAreaGetDto {
    private int areaId;
    private String areaName;
    private Date areaCreated;
    private Date areaUpdated;
}
