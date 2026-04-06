package com.elevata.gsrtc.controller;

import com.elevata.gsrtc.dto.UserResponse;
import com.elevata.gsrtc.entity.AppUser;
import com.elevata.gsrtc.service.impl.CustomUserDetails;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthUserController {

    @GetMapping("/me")
    public ResponseEntity<?> fetchUser(
            @AuthenticationPrincipal CustomUserDetails userDetails) {

        AppUser user = userDetails.getUser();

        UserResponse response = new UserResponse();
        response.setUserId(user.getUserId());
        response.setEmail(user.getEmail());
        response.setName(user.getName());
        response.setRole(user.getRole().name());

        if (user.getBusDepot() != null) {
            response.setDepotId(user.getBusDepot().getDepotId());
        }

        return ResponseEntity.ok(response);
    }
}
