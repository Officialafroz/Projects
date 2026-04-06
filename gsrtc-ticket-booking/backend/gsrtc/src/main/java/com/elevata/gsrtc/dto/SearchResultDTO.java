package com.elevata.gsrtc.dto;

import com.elevata.gsrtc.entity.RouteStops;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class SearchResultDTO {
    private String routeName;
    private List<RouteStops> stops;
    private String tripCode;
    private String classType;
    private String departureTime;
    private String destinationTime;
    private double seatRate;
    private int vacantSeats;
}
