package com.funnyland.funnyland_server.model.video.entity;

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
@Table(name="video")
public class VideoPureEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "video_id")
    private int videoId;

    @Column(name="video_name")
    private String videoName;

    @Column(name="video_type")
    private String videoType;

    @Column(name="video_url")
    private String videoUrl;

    @Column(name="video_key")
    private String videoKey;

    @Column(name="video_display")
    private int videoDisplay;

    @Column(name="video_created")
    private Date videoCreated;

    @Column(name="video_updated")
    private Date videoUpdated;

    @Column(name="video_deleted")
    private int videoDeleted;


}
