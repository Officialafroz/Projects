package com.elevata.gsrtc.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@Data
public class BookingManagementTripDTO {
    private String busNumber;
    private String route;
    private List<String> viaPlaces;
    private String classType;
    private LocalDate tripDate;
    private String tripCode;
    private int bookedSeats;
    private int remainingSeats;
    private BigDecimal revenue;
}
