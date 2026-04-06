package com.elevata.gsrtc.controller;

import com.elevata.gsrtc.dto.PricingDTO;
import com.elevata.gsrtc.service.PricingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/central/pricing")
public class PricingController {
    private final PricingService pricingService;

    @Autowired
    public PricingController(PricingService pricingService) {
        this.pricingService = pricingService;
    }

    @GetMapping
    public PricingDTO getPricing() {
        return pricingService.getPricing();
    }

    @PostMapping
    public ResponseEntity<String> save(@RequestBody PricingDTO pricingDTO) {
        return ResponseEntity.ok(pricingService.save(pricingDTO));
    }

    @PutMapping
        public ResponseEntity<String> updatePricing(
                @RequestParam(required = false) Double reservationFee,
                @RequestParam(required = false) Double tollFee,
                @RequestParam(required = false) Double gst
        ) {
        pricingService.updatePricing(reservationFee, tollFee, gst);
        return ResponseEntity.ok("Pricing updated successfully");
    }
}
