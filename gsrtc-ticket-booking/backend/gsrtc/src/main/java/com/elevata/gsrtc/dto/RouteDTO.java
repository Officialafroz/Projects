package com.elevata.gsrtc.dto;

import com.elevata.gsrtc.entity.RouteStops;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@Setter
@Getter
public class RouteDTO {
    private int routeId;
    private String routeName;
    private String startingPoint;
    private String endingPoint;
    private String classType;
    private double distance;
    private int duration;
    private int depotId;
}
