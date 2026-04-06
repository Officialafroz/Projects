package com.elevata.gsrtc.controller;

import com.elevata.gsrtc.dto.AdminLoginDTO;
import com.elevata.gsrtc.dto.EmailRequestDTO;
import com.elevata.gsrtc.dto.OtpRequestDTO;
import com.elevata.gsrtc.service.LoginService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth/login")
public class LoginController {
    private final LoginService loginService;

    @Autowired
    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @PostMapping("/process-email")
    public ResponseEntity<String> getUserEmail(@RequestBody EmailRequestDTO dto) {
        System.out.println("-".repeat(100));
        loginService.processLogin(dto);
        return ResponseEntity.ok("OTP sent to user.");
    }

    @PostMapping("/verify/otp")
    public ResponseEntity<?> verify(
            @RequestBody OtpRequestDTO otpRequest,
            HttpServletResponse response) {
        loginService.verify(otpRequest, response);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/verify/credentials")
    public ResponseEntity<AdminLoginDTO> verify(
            @RequestBody AdminLoginDTO dto,
            HttpServletResponse response) {
        System.out.println("-".repeat(30) + "API called");
        String verificationMessage = loginService.verify(dto, response);

        if (verificationMessage.equals("Verified depot admin")) {
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}
