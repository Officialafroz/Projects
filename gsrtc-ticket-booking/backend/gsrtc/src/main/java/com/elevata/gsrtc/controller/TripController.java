package com.elevata.gsrtc.controller;

import com.elevata.gsrtc.dto.BusTripDTO;
import com.elevata.gsrtc.dto.TripDTO;
import com.elevata.gsrtc.entity.Trip;
import com.elevata.gsrtc.service.TripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/depot/trip")
public class TripController {
    private TripService tripService;

    @Autowired
    public TripController(TripService tripService) {
        this.tripService = tripService;
    }

    @GetMapping("/trips")
    public List<Trip> getTripList() {
        return tripService.findAll();
    }

    @GetMapping("/{tripId}")
    public Trip getTripById(@PathVariable int tripId) {
        return tripService.findById(tripId);
    }

    @GetMapping("/tripCode")
    public String findTripCode() {
        return tripService.generateTripCode("0050", "Gandhinagar", "Surat", "Premium", 2);
    }

    @GetMapping("/list/{depotId}")
    public List<TripDTO> findAllByDepotId(@PathVariable int depotId) {
        return tripService.findAllByDepotId(depotId);
    }

    @PostMapping
    public ResponseEntity<String> add(@RequestBody BusTripDTO busTripDTO) {
        tripService.save(busTripDTO);
        return ResponseEntity.ok("Bus Trip added.");
    }

    @DeleteMapping("/delete/{tripId}")
    public void delete(@PathVariable int tripId) {
        tripService.delete(tripId);
    }
}
