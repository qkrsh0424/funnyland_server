package com.funnyland.funnyland_server.controller.api;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/token")
public class TokenApiController {
    @Autowired
    CsrfTokenRepository csrfTokenRepository;
    @GetMapping("/get/csrf")
    public void GetCsrfToken(HttpServletRequest request, HttpServletResponse response){
        CsrfToken csrfToken = csrfTokenRepository.generateToken(request);
        csrfTokenRepository.saveToken(csrfToken, request, response);
        // System.out.println(csrfTokenRepository.loadToken(request).getToken());
    }
}
