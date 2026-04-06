package com.elevata.gsrtc.repository;

import com.elevata.gsrtc.entity.AppUser;
import com.elevata.gsrtc.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Integer> {
    Optional<RefreshToken> findByToken(String token);

    Optional<RefreshToken> findByUser(AppUser user);

    void deleteByUser(AppUser user);

    void deleteByToken(String refreshToken);
}
