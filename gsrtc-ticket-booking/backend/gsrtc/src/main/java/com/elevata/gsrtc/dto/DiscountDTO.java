package com.elevata.gsrtc.dto;

import com.elevata.gsrtc.enums.DiscountType;
import lombok.Data;

@Data
public class DiscountDTO {
    private Long id;
    private String name;
    private DiscountType discountType;
    private Double discountValue;
    private Boolean active;

    public DiscountDTO(Long id, String name, DiscountType discountType, Double discountValue, Boolean active) {
        this.id = id;
        this.name = name;
        this.discountType = discountType;
        this.discountValue = discountValue;
        this.active = active;
    }
}
