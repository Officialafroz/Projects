package com.elevata.gsrtc.dto;

import lombok.Data;

@Data
public class BusDTO {
    private String busNumber;
    private String busType;
    private int totalSeats;
    private int depotId;
}
