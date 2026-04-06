package com.elevata.gsrtc.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TripDTO {
    private String routeName;
    private String busNumber;
    private LocalDate date;
    private LocalDateTime departureTime;
    private LocalDateTime destinationTime;
    private String status;
}
