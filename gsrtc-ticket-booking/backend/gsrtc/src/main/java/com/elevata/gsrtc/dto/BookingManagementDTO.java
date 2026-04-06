package com.elevata.gsrtc.dto;

import lombok.*;

import java.time.LocalDate;
import java.util.Set;


@Data
public class BookingManagementDTO {
    private String pnr;
    private String tripCode;
    private String route;
    private LocalDate journeyDate;
    private String classType;
    private Set<Integer> seats;
    private double totalFare;
}
