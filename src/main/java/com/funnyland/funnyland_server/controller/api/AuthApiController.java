package com.funnyland.funnyland_server.controller.api;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.funnyland.funnyland_server.model.user.LoginRequestDto;
import com.funnyland.funnyland_server.model.user.dto.UserLoginDTO;
import com.funnyland.funnyland_server.service.user.UserAuthService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// @CrossOrigin(origins = {"http://localhost:3000","null"})
@RestController
@RequestMapping("/api/auth")
public class AuthApiController {
    @Autowired
    UserAuthService userAuthService;


    // /api/auth/login
    @PostMapping("/login")
    public String AuthLogin(HttpServletRequest request,HttpServletResponse response, @RequestBody UserLoginDTO userLoginDto){
        if(userAuthService.isUserSessionValid(request)){
            return "{\"message\":\"error\"}";
        }
        if(userAuthService.checkUserLogin(userLoginDto, request)){
            return "{\"message\":\"success\"}";
        }
        return "{\"message\":\"failure\"}";
    }

    @GetMapping("/check/loged")
    public String AuthCheckLoged(HttpServletRequest request){
        if(userAuthService.isUserSessionValid(request)){
            return "{\"message\":\"success\"}";
        }else{
            return "{\"message\":\"failure\"}";
        }
    }

    // TODO LOGOUT AJAX 로 넘기기
    @PostMapping(value = "/logout")
    public String LogoutDo(HttpServletRequest request, HttpServletResponse response) throws Exception {
        userAuthService.logout(request);
        return "{\"message\":\"success\"}";
    }
}
