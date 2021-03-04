package com.funnyland.funnyland_server.model.video.dto;

import java.util.Date;

import lombok.Data;

@Data
public class VideoGetDto {
    private int videoId;
    private String videoName;
    private String videoType;
    private String videoUrl;
    private String videoKey;
    private int videoDisplay;
    private Date videoCreated;
    private Date videoUpdated;
}
