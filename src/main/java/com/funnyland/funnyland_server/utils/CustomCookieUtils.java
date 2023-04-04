package com.funnyland.funnyland_server.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;

@Component
public class CustomCookieUtils {
    public static String COOKIE_DOMAIN;
    public static boolean SECURE = false; // PROD : true | DEV : false

    public final static Integer CSRF_TOKEN_COOKIE_EXPIRATION = 5; // seconds - 5s

    public final static Integer ACCESS_TOKEN_COOKIE_EXPIRATION = 5 * 24 * 60 * 60; // seconds - 5일

    public final static String COOKIE_NAME_ACCESS_TOKEN = "access_token";
    public final static String COOKIE_NAME_API_CSRF_TOKEN = "api_csrf_token";
    public final static String COOKIE_NAME_X_API_CSRF_TOKEN = "x_api_csrf_token";
    public final static String COOKIE_NAME_AUTH_CSRF_TOKEN = "auth_csrf_token";
    public final static String COOKIE_NAME_X_AUTH_CSRF_TOKEN = "x_auth_csrf_token";


    @Value("${app.cookie.domain}")
    public void setCookieDomain(String domain) {
        CustomCookieUtils.COOKIE_DOMAIN = domain;
    }

    @Value("${app.cookie.secure}")
    public void setCookieSecure(boolean secure) {
        CustomCookieUtils.SECURE = secure;
    }

    public static void clearCookieForName(HttpServletResponse response, String cookieName){
        ResponseCookie cookie = ResponseCookie.from(cookieName, null)
                .httpOnly(true)
                .domain(CustomCookieUtils.COOKIE_DOMAIN)
                .secure(CustomCookieUtils.SECURE)
                .sameSite("Strict")
                .path("/")
                .maxAge(0)
                .build();

        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
    }
}
