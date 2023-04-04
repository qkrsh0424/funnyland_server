package com.funnyland.funnyland_server.controller.api;

import com.funnyland.funnyland_server.model.csrf.service.CsrfTokenService;
import com.funnyland.funnyland_server.model.message_v2.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/token")
@RequiredArgsConstructor
public class TokenApiController {
    private final CsrfTokenService csrfTokenService;

    @GetMapping("/csrf")
    public ResponseEntity<?> getCsrfToken(HttpServletRequest request, HttpServletResponse response){
        Message message = new Message();

        csrfTokenService.getCsrfToken(response);
        message.setStatus(HttpStatus.OK);
        message.setMessage("success");
        message.setData("csrf");

        return new ResponseEntity<>(message, message.getStatus());
    }
}
