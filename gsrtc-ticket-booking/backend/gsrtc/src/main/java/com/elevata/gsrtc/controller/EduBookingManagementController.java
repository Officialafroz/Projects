package com.elevata.gsrtc.controller;

import com.elevata.gsrtc.dto.EduBookingResponseDTO;
import com.elevata.gsrtc.service.EduBookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/end-user/edu-booking")
public class EduBookingManagementController {
    private final EduBookingService eduBookingService;

    @Autowired
    public EduBookingManagementController(EduBookingService eduBookingService) {
        this.eduBookingService = eduBookingService;
    }

    // Pagination
    @GetMapping("/list")
    public ResponseEntity<List<EduBookingResponseDTO>> findAllBookings() {
        return ResponseEntity.ok(eduBookingService.findAllBookings());
    }

    @PostMapping("/{id}")
    public ResponseEntity<String> saveEducationalBooking(@PathVariable Integer id) {
        return ResponseEntity.ok(eduBookingService.save(id));
    }
}
