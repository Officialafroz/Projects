package com.elevata.gsrtc.service;

import com.elevata.gsrtc.entity.AppUser;
import com.elevata.gsrtc.entity.RefreshToken;
import org.springframework.stereotype.Service;

@Service
public interface RefreshTokenService {
    RefreshToken createOrUpdateRefreshToken(AppUser user);

    RefreshToken validateRefreshToken(String token);

    void deleteByUser(AppUser user);

    void deleteByToken(String refreshToken);
}
