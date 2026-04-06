package com.elevata.gsrtc.controller;

import com.elevata.gsrtc.service.LogoutService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth/logout")
public class LogoutController {
    private final LogoutService logoutService;

    @Autowired
    public LogoutController(LogoutService logoutService) {
        this.logoutService = logoutService;
    }

    // No public access + won't work for super admin
    @PostMapping("/logout")
    public ResponseEntity<String> logoutDepotAdmin(
            HttpServletRequest request,
            HttpServletResponse response,
            @RequestParam(required = false) int depotId
    ) {
        boolean success = logoutService.logoutDepotAdmin(request, response, depotId);

        if (success) {
            return ResponseEntity.ok("Depot admin logged out successfully");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Logout failed");
        }
    }

}
