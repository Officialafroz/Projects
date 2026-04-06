package com.elevata.gsrtc.repository;

import com.elevata.gsrtc.dto.PricingDTO;
import com.elevata.gsrtc.entity.PricingConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PricingConfigRepository extends JpaRepository<PricingConfig, Integer> {

    @Query("""
            SELECT new com.elevata.gsrtc.dto.PricingDTO(
                p.reservationFee,
                p.tollFee,
                p.gstPercentage,
                p.active
            ) FROM PricingConfig p
    """)
    Optional<PricingDTO> getPricing();
}
