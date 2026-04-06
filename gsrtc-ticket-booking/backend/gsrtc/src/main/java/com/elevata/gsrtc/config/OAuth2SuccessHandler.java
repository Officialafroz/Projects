package com.elevata.gsrtc.config;

import com.elevata.gsrtc.entity.AppUser;
import com.elevata.gsrtc.entity.RefreshToken;
import com.elevata.gsrtc.enums.AccountStatus;
import com.elevata.gsrtc.enums.Role;
import com.elevata.gsrtc.repository.AppUserRepository;
import com.elevata.gsrtc.service.CookieService;
import com.elevata.gsrtc.service.RefreshTokenService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {

    private final JwtProvider jwtProvider;
    private final RefreshTokenService refreshTokenService;
    private final AppUserRepository userRepository;
    private final CookieService cookieService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication
    ) throws IOException {
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();

        String email = oAuth2User.getAttribute("email");
        String name = oAuth2User.getAttribute("name");

        AppUser user = userRepository.findByEmail(email)
                .orElseGet(() -> {
                    AppUser newUser = new AppUser();
                    newUser.setName(name);
                    newUser.setEmail(email);
                    newUser.setProvider("GOOGLE");
                    newUser.setRole(Role.END_USER);
                    newUser.setAccountStatus(AccountStatus.ACTIVE);

                    return userRepository.save(newUser);
                });

        String accessToken = jwtProvider.generateAccessToken(user);
        RefreshToken refreshToken = refreshTokenService.createOrUpdateRefreshToken(user);

        cookieService.addAccessTokenCookie(response, accessToken);
        cookieService.addRefreshTokenCookie(response, refreshToken.getToken());

        request.getSession().invalidate();
        SecurityContextHolder.clearContext();

        response.sendRedirect("http://localhost:5173/");
    }
}
