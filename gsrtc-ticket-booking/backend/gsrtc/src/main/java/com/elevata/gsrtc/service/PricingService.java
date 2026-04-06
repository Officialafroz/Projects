package com.elevata.gsrtc.service;

import com.elevata.gsrtc.dto.PricingDTO;
import org.springframework.stereotype.Service;

@Service
public interface PricingService {
    PricingDTO getPricing();

    void updatePricing(Double reservationFee, Double tollFee, Double gst);

    String save(PricingDTO dto);
}
