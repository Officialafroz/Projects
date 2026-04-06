package com.elevata.gsrtc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class OtpService {
    private EmailService emailService;
    private static Random random = new Random();

    @Autowired
    public OtpService(EmailService emailService) {
        this.emailService = emailService;
    }

    public static int generateOtp() {
        return 100000 + random.nextInt(900000);
    }

    public void sendOtp(String email) {
        int otp = OtpService.generateOtp();

        emailService.sendEmail(email, "Login code", "Your OTP is " + otp);
    }
}
