package com.funnyland.funnyland_server.model.user;

import lombok.Data;

@Data
public class LoginRequestDto {
    private String username;
    private String password;
}
