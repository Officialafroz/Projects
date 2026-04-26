package com.elevata.gsrtc.controller;

import com.elevata.gsrtc.dto.PassengerDTO;
import com.elevata.gsrtc.entity.Passenger;
import com.elevata.gsrtc.service.PassengerService;
import com.elevata.gsrtc.service.impl.PassengerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/end-user/passenger")
public class PassengerController {
    private final PassengerService passengerService;

    @Autowired
    public PassengerController(PassengerServiceImpl passengerService) {
        this.passengerService = passengerService;
    }

    @PostMapping("/save")
    public ResponseEntity<String> save(@RequestBody List<PassengerDTO> passengers,
                                       @RequestParam String tripCode,
                                       @RequestParam String pnr
    ) {
        return ResponseEntity.ok(passengerService.save(passengers, tripCode, pnr));
    }

    @GetMapping("/individual-fare")
    public double getFare(
            @RequestParam String boardingPoint,
            @RequestParam String destination,
            @RequestParam String tripCode
    ) {
        return passengerService.getFare(boardingPoint, destination, tripCode);
    }

    @DeleteMapping("/delete/{passengerId}")
    public void delete(@PathVariable int passengerId) {
        passengerService.delete(passengerId);
    }
}
