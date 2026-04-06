package com.elevata.gsrtc.service;

import com.elevata.gsrtc.dto.PassengerDTO;
import com.elevata.gsrtc.entity.Passenger;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PassengerService {
    List<Passenger> findByBookingId(int bookingId);

    Passenger findById(int passengerId);

    List<Passenger> findAll();

    String save(List<PassengerDTO> passengers, String tripCode, String pnr);

    void save(Passenger passenger);

    double getFare(String boardingPoint, String destination, String tripCode);

    void delete(int passengerId);
}
