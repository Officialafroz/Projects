package com.elevata.gsrtc.dto;

import lombok.Data;

@Data
public class PricingDTO {
    private Double reservationFee;
    private Double tollFee;
    private Double gstPercentage;
    private boolean active;

    public PricingDTO(Double reservationFee, Double tollFee, Double gstPercentage, boolean active) {
        this.reservationFee = reservationFee;
        this.tollFee = tollFee;
        this.gstPercentage = gstPercentage;
        this.active = active;
    }
}
