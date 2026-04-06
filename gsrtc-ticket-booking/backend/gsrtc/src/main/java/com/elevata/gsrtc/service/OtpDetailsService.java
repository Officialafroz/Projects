package com.elevata.gsrtc.service;

import com.elevata.gsrtc.entity.AppUser;
import com.elevata.gsrtc.entity.OtpDetails;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface OtpDetailsService {
    List<OtpDetails> getOtpList();

    OtpDetails findOtpDetailsById(int otpId);

    OtpDetails findOtpDetailByOtp(String otp);

    void save(OtpDetails otpDetails);

    void deleteById(int otpId);

    OtpDetails getOtpDetailsByUser(AppUser appUser);
}
