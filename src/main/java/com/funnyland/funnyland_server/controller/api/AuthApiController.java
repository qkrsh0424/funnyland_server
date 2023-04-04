package com.funnyland.funnyland_server.controller.api;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.funnyland.funnyland_server.annotation.RequiredLogin;
import com.funnyland.funnyland_server.model.message_v2.Message;
import com.funnyland.funnyland_server.model.user.dto.UserLoginDTO;
import com.funnyland.funnyland_server.service.user.UserAuthService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthApiController {
    @Autowired
    UserAuthService userAuthService;


    // /api/auth/login
    @PostMapping("/login")
    public ResponseEntity<?> AuthLogin(HttpServletRequest request,HttpServletResponse response, @RequestBody UserLoginDTO userLoginDto){
        Message message = new Message();

        userAuthService.login(request, response, userLoginDto);
        message.setStatus(HttpStatus.OK);
        message.setMessage("success");

        return new ResponseEntity<>(message, message.getStatus());
    }

    @GetMapping("/check/loged")
    @RequiredLogin
    public ResponseEntity<?> AuthCheckLoged(HttpServletRequest request){
        Message message = new Message();

        userAuthService.checkLogin();
        message.setStatus(HttpStatus.OK);
        message.setMessage("success");

        return new ResponseEntity<>(message, message.getStatus());
    }

    @PostMapping("/reissue/access-token")
    public ResponseEntity<?> reissueAccessToken(HttpServletRequest request, HttpServletResponse response) {
        Message message = new Message();

        userAuthService.reissueAccessToken(request, response);
        message.setStatus(HttpStatus.OK);
        message.setMessage("success");

        return new ResponseEntity<>(message, message.getStatus());
    }

    @PostMapping(value = "/logout")
    @RequiredLogin
    public ResponseEntity<?> LogoutDo(HttpServletRequest request, HttpServletResponse response) {
        Message message = new Message();
        userAuthService.logout(request, response);
        message.setStatus(HttpStatus.OK);
        message.setMessage("success");
        return new ResponseEntity<>(message, message.getStatus());
    }
}
