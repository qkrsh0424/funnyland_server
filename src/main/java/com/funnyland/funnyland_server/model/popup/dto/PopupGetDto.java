package com.funnyland.funnyland_server.model.popup.dto;

import java.util.Date;

import lombok.Data;

@Data
public class PopupGetDto {
    private int popupId;
    private String popupName;
    private String popupImageUrl;
    private String popupUrl;
    private String popupType;
    private Date popupCreated;
    private Date popupUpdated;
}
