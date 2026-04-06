package com.elevata.gsrtc.service.impl;

import com.elevata.gsrtc.repository.OtpDetailsRepository;
import com.elevata.gsrtc.entity.OtpDetails;
import com.elevata.gsrtc.entity.AppUser;
import com.elevata.gsrtc.service.OtpDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class OtpDetailsServiceImpl implements OtpDetailsService {
    private final OtpDetailsRepository otpDetailsRepository;

    @Autowired
    public OtpDetailsServiceImpl(OtpDetailsRepository otpDetailsRepository) {
        this.otpDetailsRepository = otpDetailsRepository;
    }

    @Override
    public List<OtpDetails> getOtpList() {
        return otpDetailsRepository.findAll();
    }

    @Override
    public OtpDetails findOtpDetailsById(int otpId) {
        Optional<OtpDetails> otpDetails = otpDetailsRepository.findById(otpId);

        if (otpDetails.isPresent()) {
            return otpDetails.get();
        } else {
            throw new RuntimeException("Otp details not found for id");
        }
    }

    @Override
    public OtpDetails findOtpDetailByOtp(String otp) {
        return otpDetailsRepository.findOtpDetailByOtp(otp);
    }

    @Transactional
    @Override
    public void save(OtpDetails otpDetails) {
        otpDetailsRepository.save(otpDetails);
        System.out.println("Otp saved to DB.");
    }

    @Transactional
    @Override
    public void deleteById(int otpId) {
        otpDetailsRepository.deleteById(otpId);
    }

    @Override
    public OtpDetails getOtpDetailsByUser(AppUser appUser) {
        return otpDetailsRepository.findByUser(appUser)
                .orElseThrow(() -> new RuntimeException("Otp not found."));
    }
}
