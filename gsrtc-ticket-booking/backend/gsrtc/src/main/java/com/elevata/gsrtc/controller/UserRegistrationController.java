package com.elevata.gsrtc.controller;

import com.elevata.gsrtc.dto.EmailRequestDTO;
import com.elevata.gsrtc.dto.OtpRequestDTO;
import com.elevata.gsrtc.service.RegistrationService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/end-user/register")
public class UserRegistrationController {
    private RegistrationService registrationService;

    public UserRegistrationController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @PostMapping("/process/email")
    public void getUserEmail(@RequestBody EmailRequestDTO emailRequestDTO) {
        registrationService.processRegistration(emailRequestDTO);
    }

    @PostMapping("/verify")
    public String verify(@RequestBody OtpRequestDTO otpRequest) {
        return registrationService.verify(otpRequest);
    }
}
