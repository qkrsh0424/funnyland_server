package com.funnyland.funnyland_server.model.popup.entity;

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
@Table(name = "popup")
public class PopupPureEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int popupId;
    private String popupName;
    private String popupImageUrl;
    private String popupUrl;
    @Column(name="popup_type")
    private String popupType;
    private Date popupCreated;
    private Date popupUpdated;
    private int popupDeleted;
}
