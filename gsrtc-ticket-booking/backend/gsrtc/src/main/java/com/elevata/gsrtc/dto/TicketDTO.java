package com.elevata.gsrtc.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class TicketDTO {
    private String tripCode;
    private String pnr;
    private String route;
    private LocalDate journeyDate;
    private String classType;
    private String boardingTime;
    private List<String> passengers;
    private Set<Integer> seats;
    private double totalFare;
}
