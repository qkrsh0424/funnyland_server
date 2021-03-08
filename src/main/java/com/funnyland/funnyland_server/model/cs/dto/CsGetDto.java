package com.funnyland.funnyland_server.model.cs.dto;

import java.util.Date;

import lombok.Data;

@Data
public class CsGetDto {
    private int csId;
    private String csType;
    private String csTitle;
    private String csDesc;
    private String csAuthor;
    private boolean csImportantChecked;
    private int csViewCount;
    private Date csCreated;
    private Date csUpdated;
}
