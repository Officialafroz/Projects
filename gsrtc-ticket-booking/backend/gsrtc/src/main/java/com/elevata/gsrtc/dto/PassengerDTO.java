package com.elevata.gsrtc.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PassengerDTO {
    private String fullName;
    private int age;
    private String gender;
    private String boardingPoint;
    private String destination;
    private int seat;
    private double fare;
}

