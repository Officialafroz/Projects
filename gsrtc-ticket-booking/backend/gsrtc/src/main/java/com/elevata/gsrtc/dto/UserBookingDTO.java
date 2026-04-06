package com.elevata.gsrtc.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserBookingDTO {
    private String email;
    private String tripCode;
    private LocalDate journeyDate;
    private double totalFare;
}
