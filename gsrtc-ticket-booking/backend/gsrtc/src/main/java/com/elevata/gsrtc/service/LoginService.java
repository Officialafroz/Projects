package com.elevata.gsrtc.service;

import com.elevata.gsrtc.dto.AdminLoginDTO;
import com.elevata.gsrtc.dto.EmailRequestDTO;
import com.elevata.gsrtc.dto.OtpRequestDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Service;

@Service
public interface LoginService {
    void processLogin(EmailRequestDTO emailRequestDTO);

    void verify(OtpRequestDTO request, HttpServletResponse response);

    String verify(AdminLoginDTO dto, HttpServletResponse response);
}
