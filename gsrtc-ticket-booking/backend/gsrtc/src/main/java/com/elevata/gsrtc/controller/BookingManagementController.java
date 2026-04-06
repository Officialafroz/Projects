package com.elevata.gsrtc.controller;

import com.elevata.gsrtc.dto.BookingManagementTripDTO;
import com.elevata.gsrtc.service.BookingManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/depot/booking-management")
public class BookingManagementController {
    private final BookingManagementService bookingManagementService;

    @Autowired
    public BookingManagementController(BookingManagementService bookingManagementService) {
        this.bookingManagementService = bookingManagementService;
    }

    // Add pagination
    @GetMapping("/list")
    public ResponseEntity<List<BookingManagementTripDTO>> findAll(
            @RequestParam int depotId
    ) {
        return ResponseEntity.ok(bookingManagementService.findAll(depotId));
    }

    @GetMapping("/filter/date")
    public ResponseEntity<List<BookingManagementTripDTO>> findAllByDate(
            @RequestParam int depotId,
            @RequestParam @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate date
    ) {
        return ResponseEntity.ok(bookingManagementService.findAllByDate(depotId, date));
    }

    @GetMapping("/filter/trip-code")
    public ResponseEntity<BookingManagementTripDTO> filterByTripCode(
            @RequestParam int depotId,
            @RequestParam String tripCode
    ) {
        return ResponseEntity.ok(bookingManagementService
                .filterByTripCode(depotId, tripCode));
    }

    //Add error handling
    @GetMapping("/filter/route/class-type/revenue")
    public ResponseEntity<List<BookingManagementTripDTO>> filterByRouteAndClassAndRevenue(
            @RequestParam int depotId,
            @RequestParam String route,
            @RequestParam String classType,
            @RequestParam BigDecimal revenue
    ) {
        return ResponseEntity.ok(bookingManagementService
                .filterByRouteAndClassAndRevenue(depotId, route, classType, revenue));
    }
}
