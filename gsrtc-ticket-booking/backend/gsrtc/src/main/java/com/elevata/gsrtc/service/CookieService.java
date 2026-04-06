package com.elevata.gsrtc.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Service;

@Service
public interface CookieService {
    void clearTokenCookie(HttpServletResponse response, String tokenType);

    void addRefreshTokenCookie(HttpServletResponse response, String token);

    void addAccessTokenCookie(HttpServletResponse response, String token);

    String extractRefreshTokenFromCookie(HttpServletRequest request);

}
