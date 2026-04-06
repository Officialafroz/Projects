package com.elevata.gsrtc.service.impl;

import com.elevata.gsrtc.entity.AppUser;
import com.elevata.gsrtc.entity.RefreshToken;
import com.elevata.gsrtc.repository.RefreshTokenRepository;
import com.elevata.gsrtc.service.RefreshTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.UUID;

@Service
public class RefreshTokenServiceImpl implements RefreshTokenService {

    private static final long REFRESH_TOKEN_EXPIRATION_DAYS = 7;
    private final RefreshTokenRepository refreshTokenRepository;

    @Autowired
    public RefreshTokenServiceImpl(RefreshTokenRepository refreshTokenRepository) {
        this.refreshTokenRepository = refreshTokenRepository;
    }

    public RefreshTokenRepository getRefreshTokenRepository() {
        return refreshTokenRepository;
    }

    @Override
    @Transactional
    public RefreshToken createOrUpdateRefreshToken(AppUser user) {

        SecureRandom secureRandom = new SecureRandom();
        byte[] bytes = new byte[64];
        secureRandom.nextBytes(bytes);
        String newToken = Base64.getUrlEncoder().withoutPadding()
                .encodeToString(bytes);

        LocalDateTime expiryDate = LocalDateTime.now().plusDays(REFRESH_TOKEN_EXPIRATION_DAYS);

        return refreshTokenRepository.findByUser(user)
                .map(existingToken -> {
                    existingToken.setToken(newToken);
                    existingToken.setExpiresAt(expiryDate);

                    return refreshTokenRepository.save(existingToken);
                })
                .orElseGet(() -> {
                    RefreshToken refreshToken = new RefreshToken();
                    refreshToken.setToken(newToken);
                    refreshToken.setExpiresAt(expiryDate);
                    refreshToken.setUser(user);

                    return refreshTokenRepository.save(refreshToken);
                });
    }

    @Override
    @Transactional(readOnly = true)
    public RefreshToken validateRefreshToken(String token) {

        RefreshToken refreshToken = refreshTokenRepository.findByToken(token)
                .orElseThrow(() -> new RuntimeException("Invalid refresh token"));

        if (refreshToken.getExpiresAt().isBefore(LocalDateTime.now())) {
            refreshTokenRepository.delete(refreshToken);
            throw new RuntimeException("Refresh token expired");
        }

        return refreshToken;
    }

    @Override
    @Transactional
    public void deleteByUser(AppUser user) {
        refreshTokenRepository.findByUser(user)
                .ifPresent(refreshTokenRepository::delete);
    }

    @Override
    public void deleteByToken(String refreshToken) {
        refreshTokenRepository.deleteByToken(refreshToken);
    }
}
