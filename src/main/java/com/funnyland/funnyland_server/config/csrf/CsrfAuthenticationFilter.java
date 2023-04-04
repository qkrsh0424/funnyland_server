package com.funnyland.funnyland_server.config.csrf;

import com.funnyland.funnyland_server.config.exception.CsrfAccessDeniedException;
import com.funnyland.funnyland_server.config.exception.CsrfExpiredJwtException;
import com.funnyland.funnyland_server.config.exception.CsrfNullPointerException;
import com.funnyland.funnyland_server.utils.CsrfTokenUtils;
import com.funnyland.funnyland_server.utils.CustomCookieUtils;
import com.funnyland.funnyland_server.utils.CustomJwtUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;
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

/**
 * 수정
 * 1. csrf 쿠키를 두개를 넘겨준다. (1) => csrf_jwt_token : csrf_token 값을 가진 JWT, (2) => csrf_token : UUID
 */
@Slf4j
public class CsrfAuthenticationFilter extends OncePerRequestFilter {
    /**
     * wild card => **
     * example => POST:/wsc/**
     */
    final static List<String> excludeRequestMethodAndPath = Arrays.asList(
    );

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        String CSRF_TOKEN_SECRET = CsrfTokenUtils.getCsrfTokenSecret();
        String requestMethod = request.getMethod();
        String requestPath = request.getServletPath();
        String requestMethodAndPath = requestMethod + ":" + requestPath;

        // GET 메소드는 CsrfFilter를 타지 않는다
        if (requestMethod.equals("GET")) {
            chain.doFilter(request, response);
            return;
        } else {

            // 특정 요청에 대해서는 CSRF를 타지 않도록 한다.
            if(excludeRequestMethodAndPath.contains(requestMethodAndPath)){
                chain.doFilter(request, response);
                return;
            }

            try {
                Cookie csrfTokenCookie = WebUtils.getCookie(request, CustomCookieUtils.COOKIE_NAME_AUTH_CSRF_TOKEN);

                String csrfJwtToken = csrfTokenCookie.getValue();

                String xCsrfToken = request.getHeader("X-XSRF-TOKEN");
                String secret = xCsrfToken + CSRF_TOKEN_SECRET;

                Claims claims = CustomJwtUtils.parseJwt(secret, csrfJwtToken);

                chain.doFilter(request, response);
                return;
            } catch (ExpiredJwtException e) {     // 토큰 만료
                throw new CsrfExpiredJwtException("Csrf jwt expired.");
            } catch (NullPointerException e) {
                throw new CsrfNullPointerException("Csrf cookie does not exist.");
            } catch (IllegalArgumentException e) {
                throw new CsrfNullPointerException("Csrf jwt does not exist.");
            } catch (UnsupportedJwtException e) {
                throw new CsrfAccessDeniedException("ClaimsJws argument does not represent an Claims JWS");
            } catch (MalformedJwtException e) {
                throw new CsrfAccessDeniedException("ClaimsJws string is not a valid JWS. ");
            } catch (SignatureException e) {
                throw new CsrfAccessDeniedException("ClaimsJws JWS signature validation fails");
            }
        }
    }
}
