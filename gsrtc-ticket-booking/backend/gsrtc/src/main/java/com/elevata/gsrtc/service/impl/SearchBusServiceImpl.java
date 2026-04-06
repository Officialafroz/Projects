package com.elevata.gsrtc.service.impl;

import com.elevata.gsrtc.dto.SearchResultDTO;
import com.elevata.gsrtc.entity.BusRoute;
import com.elevata.gsrtc.entity.RouteStops;
import com.elevata.gsrtc.entity.Trip;
import com.elevata.gsrtc.exception.RouteNotFoundException;
import com.elevata.gsrtc.repository.BusRouteRepository;
import com.elevata.gsrtc.repository.PassengerRepository;
import com.elevata.gsrtc.repository.RouteStopsRepository;
import com.elevata.gsrtc.repository.TripRepository;
import com.elevata.gsrtc.service.FareService;
import com.elevata.gsrtc.service.SearchBusService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class SearchBusServiceImpl implements SearchBusService {
    private TripRepository tripRepository;
    private PassengerRepository passengerRepository;
    private RouteStopsRepository stopsRepository;
    private BusRouteRepository busRouteRepository;
    private FareService fareService;

    @Autowired
    public SearchBusServiceImpl(TripRepository tripRepository, PassengerRepository passengerRepository, RouteStopsRepository stopsRepository, BusRouteRepository busRouteRepository, FareService fareService) {
        this.tripRepository = tripRepository;
        this.passengerRepository = passengerRepository;
        this.stopsRepository = stopsRepository;
        this.busRouteRepository = busRouteRepository;
        this.fareService = fareService;
    }

    @Override
    public List<SearchResultDTO> getSearchResult(
            LocalDate journeyDate, String source, String destination, int requestedSeats
    ) {
        List<Trip> dateTrips = tripRepository.findAllByTripDate(journeyDate);
        if (dateTrips.isEmpty()) {
            return new ArrayList<>();
        }

        List<SearchResultDTO> tripDTOs = new ArrayList<>();

        for (Trip trip : dateTrips) {
            System.out.println("Inside date trips");
            int totalBookings = passengerRepository.countPassengerByTripCode(trip.getTripCode());
            int remaining = trip.getBus().getBusLayout().getTotalSeats() - totalBookings;

            BusRoute route = trip.getRoute();
            List<RouteStops> stops = stopsRepository.findByBusRouteRouteId(route.getRouteId());
//            System.out.println(stops);
            stops.removeFirst();
            stops.removeLast();

            // Find source stop ON THIS route
            RouteStops sourceStop = getStop(source, route.getRouteId());

            // Find destination stop ON THIS route
            RouteStops destinationStop = getStop(destination, route.getRouteId());
//            System.out.println("Source stop" + sourceStop.getStopName());
//            System.out.println("Destination stop" + destinationStop.getStopName());

            // Skip if either stop missing or order wrong
            if (sourceStop == null || destinationStop == null) continue;
            if (sourceStop.getStopOrder() >= destinationStop.getStopOrder()) continue;
            System.out.println("Vacant seats :" + remaining);
            if (requestedSeats > remaining) {
                continue;
            }

            double fare = fareService.calcSeatRate(sourceStop, destinationStop);

            SearchResultDTO dto = makeSearchResultDTO(route.getRouteName(), trip, stops, fare, remaining);
            System.out.println("dto----" + dto);

            tripDTOs.add(dto);
        }

        return tripDTOs;
    }

    private String extractTime(LocalDateTime dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        return dateTime.format(formatter);
    }

    private RouteStops getStop(String stopName, int routeId) {

        return stopsRepository.findAllByStopName(stopName)
                .stream()
                .filter(s -> s.getBusRoute().getRouteId() == routeId)
                .findFirst()
                .orElseThrow(() -> new RouteNotFoundException(String.format("Route stop %s not found", stopName)));
    }

    private SearchResultDTO makeSearchResultDTO(
            String routeName, Trip trip, List<RouteStops> stops, double seatRate, int vacantSeats
    ) {
        return new SearchResultDTO(
                routeName,
                stops,
                trip.getTripCode(),
                trip.getBus().getBusType(),
                extractTime(trip.getDepartureTime()),
                extractTime(trip.getDestinationTime()),
                seatRate,
                vacantSeats
        );
    }
}
