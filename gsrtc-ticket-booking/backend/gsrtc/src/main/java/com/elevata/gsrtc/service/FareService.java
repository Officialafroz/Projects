package com.elevata.gsrtc.service;

import com.elevata.gsrtc.entity.RouteStops;
import org.springframework.stereotype.Service;

@Service
public class FareService {
    public double calcSeatRate(RouteStops source, RouteStops destination) {
        return destination.getFare() - source.getFare();
    }
}

