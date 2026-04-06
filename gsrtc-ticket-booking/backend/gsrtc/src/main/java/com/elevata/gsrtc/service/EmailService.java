package com.elevata.gsrtc.service;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public interface EmailService {
    @Async
    void sendEmail(String to, String subject, String text);
}
