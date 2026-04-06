package com.elevata.gsrtc.service.impl;

import com.elevata.gsrtc.entity.AppUser;
import com.elevata.gsrtc.repository.AppUserRepository;
import com.elevata.gsrtc.service.CookieService;
import com.elevata.gsrtc.service.LogoutService;
import com.elevata.gsrtc.service.RefreshTokenService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class LogoutServiceImpl implements LogoutService {
    private AppUserRepository userRepository;
    private CookieService cookieService;
    private RefreshTokenService refreshTokenService;

    public LogoutServiceImpl(AppUserRepository userRepository, CookieService cookieService, RefreshTokenService refreshTokenService) {
        this.userRepository = userRepository;
        this.cookieService = cookieService;
        this.refreshTokenService = refreshTokenService;
    }

    @Override
    public boolean logoutDepotAdmin(HttpServletRequest request, HttpServletResponse response, int depotId) {
        AppUser user = userRepository.findByBusDepotDepotId(depotId)
                .orElseThrow();

        try {
            String refreshToken = cookieService.extractRefreshTokenFromCookie(request);

            if (refreshToken != null) {
                refreshTokenService.deleteByToken(refreshToken);
            }
        } catch (Exception ignored) {}

        cookieService.clearTokenCookie(response, "accessToken");
        cookieService.clearTokenCookie(response, "refreshToken");

        SecurityContextHolder.clearContext();

        return true;
    }
}
