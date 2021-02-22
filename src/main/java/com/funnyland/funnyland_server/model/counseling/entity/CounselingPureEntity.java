package com.funnyland.funnyland_server.model.counseling.entity;

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
@Table(name = "counseling")
public class CounselingPureEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "counseling_id")
    private Long counselingId;

    @Column(name = "counseling_type")
    private String counselingType;
    
    // RSA
    @Column(name = "counseling_applier")
    private String counselingApplier;

    // RSA
    @Column(name = "counseling_phone")
    private String counselingPhone;

    @Column(name = "counseling_area")
    private String counselingArea;

    // RSA
    @Column(name = "counseling_Address")
    private String counselingAddress;

    @Column(name = "counseling_floor")
    private String counselingFloor;

    // RSA
    @Column(name = "counseling_desc")
    private String counselingDesc;

    @Column(name = "counseling_privacy_agreement")
    private String counselingPrivacyAgreement;

    @Column(name = "counseling_open_date")
    private Date counselingOpenDate;

    @Column(name = "counseling_created")
    private Date counselingCreated;

    @Column(name = "counseling_updated")
    private Date counselingUpdated;

    @Column(name = "counseling_admin_checked")
    private int counselingAdminChecked;

    @Column(name = "counseling_deleted")
    private int counselingDeleted;
}
