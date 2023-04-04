package com.funnyland.funnyland_server.config.auth;

import com.funnyland.funnyland_server.model.user.entity.UserEntity;
import com.funnyland.funnyland_server.utils.AuthTokenUtils;
import com.funnyland.funnyland_server.utils.CustomCookieUtils;
import com.funnyland.funnyland_server.utils.CustomJwtUtils;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.WebUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Slf4j
public class JwtAuthorizationFilter extends OncePerRequestFilter {
    /**
     * wild card => **
     * example => /wsc/**
     */
    final static List<String> excludeUrls = Arrays.asList(
    );


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String path = request.getServletPath();
        AntPathMatcher pathMatcher = new AntPathMatcher();

//        CSRF 발급 등 excludeUrls 에 등록된 url 은 필터를 타지 않게 한다.
        if(excludeUrls.stream().anyMatch(r->pathMatcher.match(r, path))){
            filterChain.doFilter(request, response);
            return;
        }

        Cookie jwtTokenCookie = WebUtils.getCookie(request, CustomCookieUtils.COOKIE_NAME_ACCESS_TOKEN);

        // 액세스 토큰 쿠키가 있는지 확인, 만약에 없다면 체인을 계속 타게하고 있다면 검증한다.
        if (jwtTokenCookie == null) {
            filterChain.doFilter(request, response);
            return;
        }

        String[] ipAddress = this.getClientIpAddress(request).replaceAll(" ", "").split(",");
        String clientIp = ipAddress[0];

        String accessToken = jwtTokenCookie.getValue();
        Claims claims = null;

        try {
//            액세스 토큰 정보가 유효 하다면 컨텍스트 홀더에 저장 후 필터를 계속 타게 한다.
            claims = CustomJwtUtils.parseJwt(AuthTokenUtils.getAccessTokenSecret(), accessToken);
        } catch (ExpiredJwtException e) {
            filterChain.doFilter(request, response);
            return;
        } catch (UnsupportedJwtException e) {
            filterChain.doFilter(request, response);
            return;
        } catch (MalformedJwtException e) {
            filterChain.doFilter(request, response);
            return;
        } catch (SignatureException e) {
            filterChain.doFilter(request, response);
            return;
        } catch (IllegalArgumentException e) {
            filterChain.doFilter(request, response);
            return;
        } catch (Exception e) {
            filterChain.doFilter(request, response);
            return;
        }

        if (claims == null) {
            filterChain.doFilter(request, response);
            return;
        }

        String id = UUID.fromString(claims.get("id").toString()).toString();
        String username = claims.get("username").toString();

        /*
        10.26
        TODO: IP 검증 로직은 이후에 추가해주도록 해야됨.
        먼저 auth-server 에서 로그인시 IP 정보도 claims에 담아두도록 하고 처리해야됨.
         */
//        if (!clientIp.equals(claims.get("ip"))) {
//            filterChain.doFilter(request, response);
//            return;
//        }

        UserEntity userEntity = UserEntity.builder()
                .id(id)
                .username(username)
                .build();

        this.saveAuthenticationToSecurityContextHolder(userEntity);

        filterChain.doFilter(request, response);
    }


    private void saveAuthenticationToSecurityContextHolder(UserEntity userEntity) {
        PrincipalDetails principalDetails = new PrincipalDetails(userEntity);
        // Jwt 토큰 서명을 통해서 서명이 정상이면 Authentication 객체를 만들어준다.
//         Authentication authentication = new UsernamePasswordAuthenticationToken(principalDetails, null, principalDetails.getAuthorities());
        Authentication authentication = new UsernamePasswordAuthenticationToken(principalDetails, null, null);

        // 강제로 시큐리티의 세션에 접근하여 Authentication 객체를 저장.
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    private String getClientIpAddress(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");

        if (ip == null) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null) {
            ip = request.getRemoteAddr();
        }

        return ip;
    }
}