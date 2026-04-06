package com.elevata.gsrtc.dto;

import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@Data
public class PassengerManagementDTO {
    private String passRef;
    private String name;
    private int age;
    private String gender;
    private LocalDateTime bookingTime;
    private String boardingPoint;
    private String destinationPoint;
    private int seatNo;
    private double individualFare;
}
