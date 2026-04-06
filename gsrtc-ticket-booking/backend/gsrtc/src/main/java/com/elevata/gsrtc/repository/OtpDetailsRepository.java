package com.elevata.gsrtc.repository;

import com.elevata.gsrtc.entity.OtpDetails;
import com.elevata.gsrtc.entity.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OtpDetailsRepository extends JpaRepository<OtpDetails, Integer> {
    Optional<OtpDetails> findByUser(AppUser appUser);
    OtpDetails findOtpDetailByOtp(String otp);

    @Modifying
    @Query("DELETE FROM OtpDetails o WHERE o.user = :user")
    void deleteByUser(@Param("user") AppUser user);
}
