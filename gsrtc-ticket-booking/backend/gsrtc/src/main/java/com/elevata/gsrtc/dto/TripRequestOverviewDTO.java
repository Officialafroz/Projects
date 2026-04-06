package com.elevata.gsrtc.dto;

import com.elevata.gsrtc.enums.TripStatus;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@Data
public class TripRequestOverviewDTO {
    private Integer tripRequestId;
    private String instituteName;
    private String tripName;
    private String sourceLocation;
    private Integer tripDays;
    private BigDecimal budget;
    private LocalDateTime tripStartDatetime;
    private TripStatus tripStatus;

    public TripRequestOverviewDTO(Integer tripRequestId, String instituteName, String tripName, String sourceLocation, Integer tripDays, BigDecimal budget, LocalDateTime tripStartDatetime, TripStatus tripStatus) {
        this.tripRequestId = tripRequestId;
        this.instituteName = instituteName;
        this.tripName = tripName;
        this.sourceLocation = sourceLocation;
        this.tripDays = tripDays;
        this.budget = budget;
        this.tripStartDatetime = tripStartDatetime;
        this.tripStatus = tripStatus;
    }
}
