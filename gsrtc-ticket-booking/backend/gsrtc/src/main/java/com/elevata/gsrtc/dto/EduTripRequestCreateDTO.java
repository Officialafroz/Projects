package com.elevata.gsrtc.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EduTripRequestCreateDTO {
    private Integer instituteId;
    private int depotId;
    private Long userId;

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

    public EduTripRequestCreateDTO(int depotId, String tripName, String sourceLocation, List<String> destinationLocations, List<String> hotels, Integer tripDays, Integer totalPersons, LocalDateTime tripStartDatetime, String emergencyContact) {
        this.depotId = depotId;
        this.tripName = tripName;
        this.sourceLocation = sourceLocation;
        this.destinationLocations = destinationLocations;
        this.hotels = hotels;
        this.tripDays = tripDays;
        this.totalPersons = totalPersons;
        this.tripStartDatetime = tripStartDatetime;
        this.emergencyContact = emergencyContact;
    }
}
