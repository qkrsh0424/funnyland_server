package com.funnyland.funnyland_server.model.user.dto;

import java.util.Date;

import lombok.Data;

@Data
public class UserLoginSessionDTO {
    private String status;
    private String id;
    private String username;
    private String email;
    private String userUrl;
    private String name;
    private String role;
    private Date createdAt;
    private Date updatedAt;
    private Date credentialCreatedAt;
    private Date credentialExpireAt;
    private int deleted;
}
