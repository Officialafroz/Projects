package com.elevata.gsrtc.dto;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;


@Data
public class BusTripDTO {
    private int routeId;
    private String busNumber;
    private LocalDate date;
    private LocalDateTime departureTime;
    private LocalDateTime destinationTime;
    private String status;
    private int depotId;
}
