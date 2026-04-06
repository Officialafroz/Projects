package com.elevata.gsrtc.controller;

import com.elevata.gsrtc.dto.TripRequestDetailDTO;
import com.elevata.gsrtc.dto.TripRequestOverviewDTO;
import com.elevata.gsrtc.service.TripRequestManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/depot/trip-request")
public class TripRequestManagementController {
    private final TripRequestManagementService educationalTripService;

    @Autowired
    public TripRequestManagementController(TripRequestManagementService educationalTripService) {
        this.educationalTripService = educationalTripService;
    }

    //API to update budget by admin
    @PutMapping("/{id}/budget")
    public ResponseEntity<String> updateBudget(
            @PathVariable Integer id,
            @RequestParam BigDecimal budget) {

        String response = educationalTripService.updateBudget(id, budget);

        return ResponseEntity.ok(response);
    }

    //API to update status when admin has finalized user's budget
    @PutMapping("/{id}/status")
    public ResponseEntity<String> updateStatus(
            @PathVariable Integer id,
            @RequestParam String status
    ) {
        return ResponseEntity.ok(educationalTripService.updateStatus(id, status));
    }
}
