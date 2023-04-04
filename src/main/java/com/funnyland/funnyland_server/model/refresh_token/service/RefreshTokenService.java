package com.funnyland.funnyland_server.model.refresh_token.service;

import com.funnyland.funnyland_server.model.refresh_token.entity.RefreshTokenEntity;
import com.funnyland.funnyland_server.model.refresh_token.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {
    private final RefreshTokenRepository refreshTokenRepository;

    public void saveAndModify(RefreshTokenEntity refreshTokenEntity){
        refreshTokenRepository.save(refreshTokenEntity);
    }

    public void deleteOldRefreshToken(UUID userId, Integer allowedAccessCount){
        refreshTokenRepository.deleteOldRefreshTokenForUser(userId.toString(), allowedAccessCount);
    }

    public RefreshTokenEntity searchById(UUID refreshTokenId) {
        return refreshTokenRepository.findById(refreshTokenId.toString()).orElse(null);
    }

    public void deleteAllByUserId(UUID userId) {
        refreshTokenRepository.deleteAllByUserId(userId.toString());
    }
}
