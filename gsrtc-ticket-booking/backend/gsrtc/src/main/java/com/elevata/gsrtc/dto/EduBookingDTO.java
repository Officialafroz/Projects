package com.elevata.gsrtc.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter @Setter
@Data
public class EduBookingDTO {

    private int requestId;
    private int allocatedBuses;
    private BigDecimal totalFare;
    private String bookingStatus;
}
