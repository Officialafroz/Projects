package com.elevata.gsrtc.service;

import com.elevata.gsrtc.dto.BusTripDTO;
import com.elevata.gsrtc.dto.TripDTO;
import com.elevata.gsrtc.entity.Trip;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public interface TripService {
    List<Trip> findAll();

    Trip findById(int tripId);

    void save(BusTripDTO busTripDTO);

    String generateTripCode(String departureTime, String from, String to,
                            String busClass, int depotNumber);

    List<TripDTO> findAllByDepotId(int depotId);

    void save(Trip trip);

    void delete(int tripId);
}
