package com.funnyland.funnyland_server.model.banner.dto;

import lombok.Data;

@Data
public class BannerGetDto {
    private Long id;
    private String imageUrl;
    private int hide;
    private int order;
}
