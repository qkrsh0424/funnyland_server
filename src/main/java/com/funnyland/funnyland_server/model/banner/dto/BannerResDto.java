package com.funnyland.funnyland_server.model.banner.dto;

import java.util.List;

import lombok.Data;

@Data
public class BannerResDto {
    private String message;
    private List<BannerGetDto> data;
    private int size;
}
