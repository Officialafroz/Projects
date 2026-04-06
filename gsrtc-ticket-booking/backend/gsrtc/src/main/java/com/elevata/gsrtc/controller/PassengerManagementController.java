package com.elevata.gsrtc.controller;

import com.elevata.gsrtc.dto.PassengerManagementDTO;
import com.elevata.gsrtc.service.PassengerManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/depot/passenger-management")
public class PassengerManagementController {
    private final PassengerManagementService passengerManagementService;

    @Autowired
    public PassengerManagementController(PassengerManagementService passengerManagementService) {
        this.passengerManagementService = passengerManagementService;
    }

    //Add pagination
    @GetMapping("/list")
    public ResponseEntity<List<PassengerManagementDTO>> findAll(@RequestParam Integer depotId) {
        return ResponseEntity.ok(passengerManagementService.findAll(depotId));
    }

    //Add error handling
    @GetMapping("/filter/pnr/pass-ref")
    public ResponseEntity<List<PassengerManagementDTO>> filterByPnrOrPassengerReference(
            @RequestParam(required = false) String pnr,
            @RequestParam(required = false) String passRef
    ) {
        return ResponseEntity.ok(
                passengerManagementService
                        .filterByPnrOrPassengerReference(pnr,passRef));
    }

    /*  Done
    Number of passenger in this bus or on this trip with total seats    */
    @GetMapping("/filter/bus-number/trip-code/total-seats")
    public ResponseEntity<List<PassengerManagementDTO>> filterByBusNumberOrTripCodeOrTotalSeats(
            @RequestParam(required = false) String busNumber,
            @RequestParam(required = false) String tripCode,
            @RequestParam(required = false) Integer totalSeats
    ) {
        return ResponseEntity.ok(
                passengerManagementService
                        .filterByBusNumberOrTripCodeOrTotalSeats(busNumber, tripCode, totalSeats));
    }

    @GetMapping("/filter/route/class/revenue")
    public ResponseEntity<List<PassengerManagementDTO>> filterByRouteAndBusTypeAndRevenue(
            @RequestParam String route,
            @RequestParam String classType,
            @RequestParam(required = false) BigDecimal revenue
    ) {
        return ResponseEntity.ok(
                passengerManagementService
                        .filterByRouteAndBusTypeAndRevenue(route, classType, revenue));
    }
}
