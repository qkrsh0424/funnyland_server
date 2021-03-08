package com.funnyland.funnyland_server.model.cs.entity;

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
@Table(name="cs")
public class CsPureEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="cs_id")
    private int csId;
    @Column(name="cs_type")
    private String csType;
    @Column(name="cs_title")
    private String csTitle;
    @Column(name="cs_desc")
    private String csDesc;
    @Column(name="cs_author")
    private String csAuthor;
    @Column(name="cs_important_checked")
    private boolean csImportantChecked;
    @Column(name="cs_view_count")
    private int csViewCount;
    @Column(name="cs_created")
    private Date csCreated;
    @Column(name="cs_updated")
    private Date csUpdated;
    @Column(name="cs_deleted")
    private int csDeleted;
}
