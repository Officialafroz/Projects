package com.elevata.gsrtc.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RouteStopDTO {
    private String stopName;
    private int stopOrder;
    private double distanceFromStart;
    private int duration;
    private double fare;
    private int routeId;
}
