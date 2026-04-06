package com.elevata.gsrtc.service.impl;

import com.elevata.gsrtc.config.JwtProvider;
import com.elevata.gsrtc.entity.AppUser;
import com.elevata.gsrtc.entity.RefreshToken;
import com.elevata.gsrtc.service.AuthService;
import com.elevata.gsrtc.service.CookieService;
import com.elevata.gsrtc.service.RefreshTokenService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {
    private final RefreshTokenService refreshTokenService;
    private final JwtProvider jwtProvider;
    private final CookieService cookieService;

    @Autowired
    public AuthServiceImpl(RefreshTokenService refreshTokenService, JwtProvider jwtProvider, CookieService cookieService) {
        this.refreshTokenService = refreshTokenService;
        this.jwtProvider = jwtProvider;
        this.cookieService = cookieService;
    }

    @Override
    public void refresh(HttpServletRequest request, HttpServletResponse response) {
        String refreshToken = cookieService.extractRefreshTokenFromCookie(request);
        RefreshToken tokenEntity = refreshTokenService.validateRefreshToken(refreshToken);
        AppUser user = tokenEntity.getUser();

        refreshTokenService.deleteByToken(tokenEntity.getToken());

        String newAccessToken = jwtProvider.generateAccessToken(user);

        RefreshToken newRefreshToken =
                refreshTokenService.createOrUpdateRefreshToken(user);

        cookieService.addAccessTokenCookie(response, newAccessToken);
        cookieService.addRefreshTokenCookie(response, newRefreshToken.getToken());
    }

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response) {

        try {
            String refreshToken = cookieService.extractRefreshTokenFromCookie(request);

            if (refreshToken != null) {
                refreshTokenService.deleteByToken(refreshToken);
            }
        } catch (Exception ignored) {}

        cookieService.clearTokenCookie(response, "accessToken");
        cookieService.clearTokenCookie(response, "refreshToken");

        SecurityContextHolder.clearContext();
    }

//    private void clearTokenCookie(HttpServletResponse response, String cookieName) {
//        Cookie cookie = new Cookie(cookieName, null);
//        cookie.setHttpOnly(true);
//        cookie.setSecure(false);
//        cookie.setPath("/");
//        cookie.setMaxAge(0);
//
//        response.addCookie(cookie);
//    }
//
//    public void addRefreshTokenCookie(HttpServletResponse response, String token) {
//        Cookie cookie = new Cookie("refreshToken", token);
//        cookie.setHttpOnly(true);
//        cookie.setSecure(false);
//        cookie.setPath("/");
//        cookie.setMaxAge(60 * 60 * 24 * 7);
//
//        response.addCookie(cookie);
//    }
//
//    public void addAccessTokenCookie(HttpServletResponse response, String token) {
//        Cookie cookie = new Cookie("accessToken", token);
//        cookie.setHttpOnly(true);
//        cookie.setSecure(false);
//        cookie.setPath("/");
//        cookie.setMaxAge(60 * 4);
//
//        response.addCookie(cookie);
//    }
//
//    private String extractRefreshTokenFromCookie(HttpServletRequest request) {
//
//        return Arrays.stream(request.getCookies())
//                .filter(cookie -> "refreshToken".equals(cookie.getName()))
//                .findFirst()
//                .map(Cookie::getValue)
//                .orElseThrow(() -> new RuntimeException("Refresh token not found."));
//    }
}
