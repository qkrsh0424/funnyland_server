package com.funnyland.funnyland_server.utils;

import com.funnyland.funnyland_server.model.user.entity.UserEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Configuration
public class AuthTokenUtils {
    private static String accessTokenSecret;
    private static String refreshTokenSecret;

    @Value("${app.jwt.access-token.secret}")
    private void setAccessTokenSecret(String accessTokenSecret) {
        AuthTokenUtils.accessTokenSecret = accessTokenSecret;
    }

    @Value("${app.jwt.refresh-token.secret}")
    private void setRefreshTokenSecret(String refreshTokenSecret) {
        AuthTokenUtils.refreshTokenSecret = refreshTokenSecret;
    }

    public static String getAccessTokenSecret() {
        return accessTokenSecret;
    }

    public static String getRefreshTokenSecret() {
        return refreshTokenSecret;
    }

    public static String generateAccessTokenJwt(UserEntity user, String refreshTokenId) {
        Map<String, Object> payload = new HashMap<>();

        payload.put("id", user.getId());
        payload.put("username", user.getUsername());
        payload.put("refreshTokenId", refreshTokenId);

        String accessToken = CustomJwtUtils.generateJwtToken(
                "JWT_ACT",
                payload,
                CustomJwtUtils.ACCESS_TOKEN_JWT_EXPIRATION,
                accessTokenSecret
        );

        return accessToken;
    }

    public static String generateRefreshTokenJwt(UserEntity user) {
        Map<String, Object> payload = new HashMap<>();

        payload.put("id", user.getId());
        payload.put("username", user.getUsername());

        String refreshToken = CustomJwtUtils.generateJwtToken(
                "JWT_RFT",
                payload,
                CustomJwtUtils.REFRESH_TOKEN_JWT_EXPIRATION,
                refreshTokenSecret
        );

        return refreshToken;
    }
}
