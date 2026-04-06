package com.elevata.gsrtc.repository;

import com.elevata.gsrtc.entity.RegistrationOtp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegistrationOtpRepository extends JpaRepository<RegistrationOtp, Integer> {
    RegistrationOtp findByEmail(String email);
}
