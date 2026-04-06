package com.elevata.gsrtc.dto;

import com.elevata.gsrtc.enums.TripStatus;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class TripRequestDetailDTO {
    private Integer requestId;
    private String instituteName;
    private String instituteCode;
    private String instituteType;

    private String tripName;
    private String sourceLocation;
    private List<String> destinationLocations;
    private List<String> hotels;

    private Integer tripDays;
    private Integer requestedBuses;
    private Integer totalPersons;

    private String busClass;
    private BigDecimal budget;
    private Boolean hotelRequired;

    private LocalDateTime tripStartDatetime;
    private String emergencyContact;

    private TripStatus tripStatus;
//    private LocalDateTime createdAt;
}
