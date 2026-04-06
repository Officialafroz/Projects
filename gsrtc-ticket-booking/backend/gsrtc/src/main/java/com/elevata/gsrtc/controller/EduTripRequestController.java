package com.elevata.gsrtc.controller;

import com.elevata.gsrtc.dto.EduTripRequestCreateDTO;
import com.elevata.gsrtc.dto.TripRequestDetailDTO;
import com.elevata.gsrtc.dto.TripRequestOverviewDTO;
import com.elevata.gsrtc.service.EduTripRequestService;
import com.elevata.gsrtc.service.TripRequestManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/end-user/edu-trip-request")
public class EduTripRequestController {
    private final EduTripRequestService educationalTripService;
    private final TripRequestManagementService requestManagementService;

    @Autowired
    public EduTripRequestController(EduTripRequestService educationalTripService, TripRequestManagementService requestManagementService) {
        this.educationalTripService = educationalTripService;
        this.requestManagementService = requestManagementService;
    }

    //API to get the overview
    // id - depotId
    @GetMapping("/overviews/{id}")
    public List<TripRequestOverviewDTO> getTripOverview(@PathVariable int id) {
        return requestManagementService.findAllNewestFirst(id);
    }

    //API to get the complete trip details
    @GetMapping("/{id}")
    public TripRequestDetailDTO getTripDetails(@PathVariable Integer id) {
        return requestManagementService.getTripDetails(id);
    }

    @GetMapping("/list/{id}")
    public List<TripRequestDetailDTO> getTripRequestList(@PathVariable Integer id) {
        return requestManagementService.getTripRequestList(id);
    }



    @PostMapping
    public ResponseEntity<String> createTripRequest(
            @RequestBody EduTripRequestCreateDTO tripRequest
    ) {
        return ResponseEntity.ok(educationalTripService.createTripRequest(tripRequest));
    }
//    @GetMapping("/{id}")
//    public TripRequestDetailDTO getTripDetails(
//            @PathVariable Integer id) {
//
//        return depotTripRequestService.getTripDetails(id);
//    }

//    @PostMapping("/{tripRequestId}/advance-payment")
//    public ResponseEntity<String> receiveAdvancePayment(
//            @PathVariable Integer tripRequestId,
//            @RequestParam BigDecimal paidAmount,
//            @RequestParam PaymentMethod paymentMethod
//    ) {
//
//        String response = depotTripRequestService
//                .receiveAdvancePayment(tripRequestId, paidAmount, paymentMethod);
//
//        return ResponseEntity.ok(response);
//    }
}
