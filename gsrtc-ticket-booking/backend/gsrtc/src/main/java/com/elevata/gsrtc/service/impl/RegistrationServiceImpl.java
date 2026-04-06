package com.elevata.gsrtc.service.impl;

import com.elevata.gsrtc.dto.EmailRequestDTO;
import com.elevata.gsrtc.dto.OtpRequestDTO;
import com.elevata.gsrtc.entity.RegistrationOtp;
import com.elevata.gsrtc.repository.RegistrationOtpRepository;
import com.elevata.gsrtc.service.EmailService;
import com.elevata.gsrtc.service.OtpService;
import com.elevata.gsrtc.service.RegistrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RegistrationServiceImpl implements RegistrationService {
    private final RegistrationOtpRepository otpRepository;
    private final EmailService emailService;

    @Autowired
    public RegistrationServiceImpl(RegistrationOtpRepository otpRepository, EmailService emailService) {
        this.otpRepository = otpRepository;
        this.emailService = emailService;
    }

    @Transactional
    @Override
    public void processRegistration(EmailRequestDTO emailRequestDTO) {
        String email = emailRequestDTO.getEmail();
        System.out.println(email);

        int otp = OtpService.generateOtp();
        RegistrationOtp registrationOtp = new RegistrationOtp(otp, email);

        otpRepository.save(registrationOtp);
        emailService.sendEmail(email,
                "Registration code", "Your OTP code is " + otp);
    }

    @Override
    public String verify(OtpRequestDTO otpRequest) {
        String email = otpRequest.getEmail();
        RegistrationOtp registrationOtp = otpRepository.findByEmail(otpRequest.getEmail());

        if (registrationOtp != null) {
            return "User verified";
        }
        return "Invalid email";
    }
}
