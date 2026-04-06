package com.elevata.gsrtc.controller;

import com.elevata.gsrtc.entity.AppUser;
import com.elevata.gsrtc.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping("/profile")
    public String profile(Authentication authentication) {
        AppUser user = (AppUser) authentication.getPrincipal();
        return "Hello " + user.getEmail();
    }

    @PostMapping("/refresh")
    public ResponseEntity<String> refresh(
            HttpServletRequest request, HttpServletResponse response
    ) {
        authService.refresh(request, response);

        return ResponseEntity.ok("Access token created.");
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request,
                                         HttpServletResponse response
    ) {
        authService.logout(request, response);

        return ResponseEntity.ok("Logged out successfully");
    }
}
