package com.elevata.gsrtc.service.impl;

import com.elevata.gsrtc.dto.PassengerDTO;
import com.elevata.gsrtc.entity.*;
import com.elevata.gsrtc.repository.BookingRepository;
import com.elevata.gsrtc.repository.PassengerRepository;
import com.elevata.gsrtc.repository.RouteStopsRepository;
import com.elevata.gsrtc.repository.TripRepository;
import com.elevata.gsrtc.service.PassengerService;
import com.elevata.gsrtc.service.FareService;
import com.elevata.gsrtc.service.ReferenceNumberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class PassengerServiceImpl implements PassengerService {
    private final PassengerRepository passengerRepository;
    private final ReferenceNumberService refService;
    private final TripRepository tripRepository;
    private final BookingRepository bookingRepository;
    private final RouteStopsRepository stopsRepository;
    private final FareService fareService;

    @Autowired
    public PassengerServiceImpl(PassengerRepository passengerRepository, ReferenceNumberService refService, TripRepository tripRepository, BookingRepository bookingRepository, RouteStopsRepository stopsRepository, FareService fareService) {
        this.passengerRepository = passengerRepository;
        this.refService = refService;
        this.tripRepository = tripRepository;
        this.bookingRepository = bookingRepository;
        this.stopsRepository = stopsRepository;
        this.fareService = fareService;
    }

    @Transactional
    @Override
    public String save(List<PassengerDTO> passengers, String tripCode, String pnr) {
        Booking existingBooking = bookingRepository.findByPnr(pnr);
        System.out.println(passengers);

        if (passengers.isEmpty()) {
            throw new IllegalArgumentException("Please add passengers!");
        }

        int passNumber = 1;
        for (PassengerDTO passenger : passengers) {
            Passenger psg = new Passenger(
                    existingBooking,
                    passenger.getFullName(),
                    passenger.getAge(),
                    passenger.getGender(),
                    passenger.getSeat(),
                    passenger.getBoardingPoint(),
                    passenger.getDestination(),
                    passenger.getFare(),
                    "CONFIRMED"
            );

            psg.setPassRef(refService.generatePassengerRef(pnr, passNumber++));

            System.out.println(passenger.getFullName() + " : " + passenger.getSeat());

            passengerRepository.save(psg);
            System.out.println("Passenger " + psg.getName() + " added successfully");
        }

        return "Passenger added.";
    }

    @Override
    public double getFare(String boardingPoint, String destination, String tripCode) {

        // 1. Get route from trip
        System.out.println(tripCode);
        BusRoute route = tripRepository.findByTripCode(tripCode).getRoute();
        System.out.println(route);
        List<RouteStops> stopsList = stopsRepository.findByBusRouteRouteId(route.getRouteId());
        System.out.println(stopsList);

        // 2. Variables to store the matched stops
        RouteStops sourceStop = null;
        RouteStops destinationStop = null;

        // 3. Loop and find matching stops
        for (RouteStops stop : stopsList) {
            if (stop.getStopName().equalsIgnoreCase(boardingPoint)) {
                sourceStop = stop;
            }
            if (stop.getStopName().equalsIgnoreCase(destination)) {
                destinationStop = stop;
            }
        }

        // 4. Validate
        if (sourceStop == null || destinationStop == null) {
            throw new RuntimeException("Invalid boarding or destination stop.");
        }

        if (sourceStop.getStopOrder() >= destinationStop.getStopOrder()) {
            System.out.println(sourceStop + "\n" + destinationStop);
            throw new RuntimeException("Boarding point must be before destination.");
        }

        // 5. Calculate fare correctly
        return fareService.calcSeatRate(sourceStop, destinationStop);
    }

    @Override
    public List<Passenger> findAll() {
        return passengerRepository.findAll();
    }

    @Override
    public List<Passenger> findByBookingId(int bookingId) {
        return passengerRepository.findAllByBookingBookingId(bookingId);
    }

    @Override
    public Passenger findById(int passengerId) {
        Optional<Passenger> passengerOptional = passengerRepository.findById(passengerId);

        if (passengerOptional.isEmpty()) {
            throw new RuntimeException("Passenger not found for id : " + passengerId);
        }

        return passengerOptional.get();
    }

    @Transactional
    @Override
    public void save(Passenger passenger) {
        if (passenger.getBooking() != null && passenger.getBooking().getBookingId() > 0) {
            int bookingId = passenger.getBooking().getBookingId();
            Optional<Booking> existingBooking = bookingRepository.findById(bookingId);

            if (existingBooking.isEmpty()) {
                throw new RuntimeException(
                        String.format("Invalid booking id - %d", bookingId));
            }
            passenger.setBooking(existingBooking.get());
        }
        passengerRepository.save(passenger);
        System.out.println("Refunds is successfully created.");
    }

    @Transactional
    @Override
    public void delete(int passengerId) {
        passengerRepository.deleteById(passengerId);
        System.out.println("Passenger is deleted safely.");
    }
}
