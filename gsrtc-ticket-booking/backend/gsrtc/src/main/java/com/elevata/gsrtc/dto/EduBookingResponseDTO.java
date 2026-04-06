package com.elevata.gsrtc.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter @Setter
@AllArgsConstructor
@Data
public class EduBookingResponseDTO {
    private EduTripRequestCreateDTO requestDto;
    private String pnr;
    private int allocatedBuses;
    private BigDecimal totalFare;
    private String bookingStatus;
}
