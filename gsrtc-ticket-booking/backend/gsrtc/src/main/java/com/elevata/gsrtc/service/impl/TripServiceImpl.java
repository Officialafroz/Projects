package com.elevata.gsrtc.service.impl;

import com.elevata.gsrtc.dto.BusTripDTO;
import com.elevata.gsrtc.dto.TripDTO;
import com.elevata.gsrtc.repository.BusDepotRepository;
import com.elevata.gsrtc.repository.BusRepository;
import com.elevata.gsrtc.repository.BusRouteRepository;
import com.elevata.gsrtc.repository.TripRepository;
import com.elevata.gsrtc.entity.*;
import com.elevata.gsrtc.service.TripService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TripServiceImpl implements TripService {
    private final TripRepository tripRepository;
    private final BusRepository busRepository;
    private final BusRouteRepository routeRepository;
    private final BusDepotRepository busDepotRepository;

    @Autowired
    public TripServiceImpl(TripRepository tripRepository, BusRepository busRepository, BusRouteRepository routeRepository, BusDepotRepository busDepotRepository) {
        this.tripRepository = tripRepository;
        this.busRepository = busRepository;
        this.routeRepository = routeRepository;
        this.busDepotRepository = busDepotRepository;
    }

    @Override
    public List<Trip> findAll() {
        return tripRepository.findAll();
    }

    @Override
    public Trip findById(int tripId) {
        Optional<Trip> optionalTrip = tripRepository.findById(tripId);

        if (optionalTrip.isPresent()) {
            return optionalTrip.get();
        } else {
            throw new RuntimeException("Trip not found for id: " + tripId);
        }
    }

    @Override
    @Transactional
    public void save(BusTripDTO busTripDTO) {
        Bus bus = busRepository.findByBusNumber(busTripDTO.getBusNumber());
        Optional<BusRoute> route = Optional.ofNullable(routeRepository.findById(busTripDTO.getRouteId()));
        Optional<BusDepot> busDepot = busDepotRepository.findById(busTripDTO.getDepotId());

        Trip trip = new Trip();

        String startingPoint = route.map(BusRoute::getStartPoint).orElse(null);
        String endingPoint = route.map(BusRoute::getEndPoint).orElse(null);

        if (route.isPresent()) {
            BusRoute busRoute = route.get();
            startingPoint = busRoute.getStartPoint();
            endingPoint = busRoute.getEndPoint();
            trip.setRoute(busRoute);
        }

        busDepot.ifPresent(trip::setBusDepot);

        String tripCode = generateTripCode(
                busTripDTO.getDepartureTime().toString(),
                startingPoint,
                endingPoint,
                bus.getBusType(),
                bus.getBusDepot().getDepotId()
        );

        System.out.println(tripCode);

        trip.setTripCode(tripCode);
        trip.setTripDate(busTripDTO.getDate());
        trip.setDepartureTime(busTripDTO.getDepartureTime());
        trip.setDestinationTime(busTripDTO.getDestinationTime());
        trip.setStatus(busTripDTO.getStatus());
        trip.setBus(bus);

        tripRepository.save(trip);
    }

    @Override
    public String generateTripCode(String departureTime, String from, String to,
                                   String busClass, int depotNumber) {
        // Extract only time if full datetime is passed (e.g., 2025-11-10T05:00)
        String timePart = departureTime.contains("T")
                ? departureTime.split("T")[1].replace(":", "")
                : departureTime.replace(":", "");

        if (timePart.length() < 4) {
            timePart = String.format("%04d", Integer.parseInt(timePart));
        }

        String fromPart = getCondensedName(from);
        String toPart = getCondensedName(to);
        String classPart = busClass.length() >= 2
                ? busClass.substring(0, 2).toUpperCase()
                : busClass.toUpperCase();
        String depotPart = String.format("%02d", depotNumber);

        return (timePart + fromPart + toPart + classPart + depotPart).toUpperCase();
    }

    @Override
    public List<TripDTO> findAllByDepotId(int depotId) {
        List<Trip> trips = tripRepository.findAllByBusDepotDepotId(depotId);
//        System.out.println(trips);
        List<TripDTO> tripDTOS = new ArrayList<>(trips.size());

        for (Trip trip : trips) {
            TripDTO tripDTO = new TripDTO();

            tripDTO.setRouteName(trip.getRoute().getRouteName());
            tripDTO.setBusNumber(trip.getBus().getBusNumber());
            tripDTO.setDate(trip.getTripDate());
            tripDTO.setStatus(trip.getStatus());
            tripDTO.setDepartureTime(trip.getDepartureTime());
            tripDTO.setDestinationTime(trip.getDestinationTime());
            System.out.println(tripDTO);

            tripDTOS.add(tripDTO);
        }

        return tripDTOS;
    }

    private String getCondensedName(String name) {
        int length = 3;

        if (name == null || name.isBlank()) return "XXX";
        String cleaned = name.replaceAll("[^A-Za-z]", "").toUpperCase();

        return cleaned.length() >= length ? cleaned.substring(0, length) : String.format("%-" + length + "s", cleaned).replace(' ', 'X');
    }

    @Transactional
    @Override
    public void save(Trip trip) {
        int busId = trip.getBus().getBusId();
        int routeId = trip.getRoute().getRouteId();

        if (trip.getBus() != null && trip.getRoute() != null && busId > 0 && routeId > 0) {
            Optional<Bus> existingBus = busRepository.findById(busId);
            BusRoute existingRoute = routeRepository.findById(routeId);

            if (existingBus.isEmpty() || existingRoute == null) {
                throw new RuntimeException(
                        String.format("Invalid bus id - %d or route id - %d", busId, routeId));
            }
            trip.setBus(existingBus.get());
            trip.setRoute(existingRoute);
        }

        tripRepository.save(trip);
        System.out.println("Trip is successfully created.");
    }

    @Transactional
    @Override
    public void delete(int tripId) {
        tripRepository.deleteById(tripId);
        System.out.println("Trip is safely deleted.");
    }
}
