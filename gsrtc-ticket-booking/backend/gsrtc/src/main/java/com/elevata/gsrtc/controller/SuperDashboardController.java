package com.elevata.gsrtc.controller;

import com.elevata.gsrtc.dto.GraphDTO;
import com.elevata.gsrtc.dto.SuperDashboardDTO;
import com.elevata.gsrtc.service.SuperDashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/central/dashboard")
public class SuperDashboardController {
    private final SuperDashboardService dashboardService;

    @Autowired
    public SuperDashboardController(SuperDashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping
    public SuperDashboardDTO getDashboard(
            @RequestParam int month,
            @RequestParam int year
    ) {
        return dashboardService.getDashboard(month, year);
    }

    @GetMapping("/graphs/booking-trends")
    public List<GraphDTO> getBookingTrends(@RequestParam int year){
        return dashboardService.getBookingTrends(year);
    }

    @GetMapping("/graphs/revenue-trends")
    public List<GraphDTO> getRevenueTrends(@RequestParam int year){
        return dashboardService.getRevenueTrends(year);
    }

    @GetMapping("/graphs/users-growth")
    public List<GraphDTO> getUsersGrowth(@RequestParam int year){
        return dashboardService.getUsersGrowth(year);
    }

    @GetMapping("/graphs/seat-occupancy")
    public List<GraphDTO> getSeatOccupancy(@RequestParam int year){
        return dashboardService.getSeatOccupancy(year);
    }
}
