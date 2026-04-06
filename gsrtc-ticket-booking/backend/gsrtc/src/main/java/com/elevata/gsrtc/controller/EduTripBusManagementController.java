package com.elevata.gsrtc.controller;

import com.elevata.gsrtc.service.EduTripBusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/depot/edu-trip-bus")
    public class EduTripBusManagementController {
        private final EduTripBusService eduTripBusService;

    @Autowired
    public EduTripBusManagementController(EduTripBusService eduTripBusService) {
        this.eduTripBusService = eduTripBusService;
    }

    // id - bookingId
    @PostMapping("/assignment/{id}")
    public ResponseEntity<String> saveEduTripBusAssignment(
            @PathVariable int id,
            @RequestParam String busNumber,
            @RequestParam String driverName,
            @RequestParam String conductorName) {

        return ResponseEntity.ok(eduTripBusService
                .save(id, busNumber,     driverName, conductorName));
    }
}
