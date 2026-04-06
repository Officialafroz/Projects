package com.elevata.gsrtc.service.impl;

import com.elevata.gsrtc.dto.PricingDTO;
import com.elevata.gsrtc.entity.PricingConfig;
import com.elevata.gsrtc.repository.PricingConfigRepository;
import com.elevata.gsrtc.service.PricingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PricingServiceImpl implements PricingService {
    private final PricingConfigRepository pricingRepository;

    @Autowired
    public PricingServiceImpl(PricingConfigRepository pricingRepository) {
        this.pricingRepository = pricingRepository;
    }

    @Override
    public PricingDTO getPricing() {
        return pricingRepository.getPricing()
                .orElseThrow(() -> new RuntimeException("Not found."));
    }

    @Override
    public void updatePricing(Double reservationFee, Double tollFee, Double gst) {
        PricingConfig existing = pricingRepository.findById(1)
                .orElseThrow();

        if (reservationFee != null && reservationFee > 0) {
            existing.setReservationFee(reservationFee);
        }

        if (tollFee != null && tollFee >= 0) {
            existing.setTollFee(tollFee);
        }

        if (gst != null && gst >= 0) {
            existing.setGstPercentage(gst);
        }

        pricingRepository.save(existing);
    }

    @Override
    public String save(PricingDTO dto) {
        PricingConfig pricing = new PricingConfig();
        pricing.setReservationFee(dto.getReservationFee());
        pricing.setTollFee(dto.getTollFee());
        pricing.setGstPercentage(dto.getGstPercentage());
        pricing.setActive(false);

        pricingRepository.save(pricing);

        return "Pricing saved";
    }
}
