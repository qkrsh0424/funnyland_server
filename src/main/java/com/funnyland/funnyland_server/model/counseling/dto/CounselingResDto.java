package com.funnyland.funnyland_server.model.counseling.dto;

import java.util.List;

import lombok.Data;

@Data
public class CounselingResDto {
    private String message;
    private List<CounselingGetDto> data;
    private Object page;
}
