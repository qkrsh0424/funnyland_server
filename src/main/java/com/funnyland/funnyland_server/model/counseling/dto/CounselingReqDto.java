package com.funnyland.funnyland_server.model.counseling.dto;

import java.util.Date;

import lombok.Data;

@Data
public class CounselingReqDto {
    private String counselingType;
    private String applierName;
    private String applierPhone;
    private String applierArea;
    private String address;
    private String floor;
    private Date openDate;
    private String desc;
    private String privacyAgreement;
}