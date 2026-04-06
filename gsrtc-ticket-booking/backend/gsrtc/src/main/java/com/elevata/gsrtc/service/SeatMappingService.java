package com.elevata.gsrtc.service;

import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public interface SeatMappingService {
    Set<Integer> mapBookedSeats(String tripCode);

    int findTotalSeats(String tripCode);
}
