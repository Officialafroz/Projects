package com.elevata.gsrtc.service;

import com.elevata.gsrtc.dto.EmailRequestDTO;
import com.elevata.gsrtc.dto.OtpRequestDTO;
import org.springframework.stereotype.Service;

@Service
public interface RegistrationService {
    void processRegistration(EmailRequestDTO emailRequestDTO);

    String verify(OtpRequestDTO otpRequest);
}
