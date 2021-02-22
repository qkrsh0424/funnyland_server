package com.funnyland.funnyland_server.model.user.vo;

import java.util.Date;

import lombok.Data;

@Data
public class UserInfoVO {
    private String username;
    private String email;
    private String userUrl;
    private String name;
    private String role;
    private Date createdAt;
    private Date updatedAt;
    private Date credentialCreatedAt;
    private Date credentialExpireAt;
}
