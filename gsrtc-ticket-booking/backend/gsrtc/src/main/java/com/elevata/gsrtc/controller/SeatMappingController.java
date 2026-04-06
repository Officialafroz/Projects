package com.elevata.gsrtc.controller;

import com.elevata.gsrtc.service.SeatMappingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequestMapping("/api/end-user/seats")
public class SeatMappingController {
    private SeatMappingService seatMappingService;

    @Autowired
    public SeatMappingController(SeatMappingService seatMappingService) {
        this.seatMappingService = seatMappingService;
    }

    @GetMapping("/map")
    public Set<Integer> mapBookedSeats(@RequestParam String tripCode) {
        return seatMappingService.mapBookedSeats(tripCode);
    }

    @GetMapping
    public Integer getTotalSeats(@RequestParam String tripCode) {
        return seatMappingService.findTotalSeats(tripCode);
    }

}
