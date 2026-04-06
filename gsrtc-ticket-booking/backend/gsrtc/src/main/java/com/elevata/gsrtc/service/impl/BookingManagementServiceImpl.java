package com.elevata.gsrtc.service.impl;

import com.elevata.gsrtc.dto.BookingManagementTripDTO;
import com.elevata.gsrtc.entity.RouteStops;
import com.elevata.gsrtc.entity.Trip;
import com.elevata.gsrtc.exception.InvalidTripCodeException;
import com.elevata.gsrtc.exception.TripNotFoundException;
import com.elevata.gsrtc.repository.BookingRepository;
import com.elevata.gsrtc.repository.PassengerRepository;
import com.elevata.gsrtc.repository.RouteStopsRepository;
import com.elevata.gsrtc.repository.TripRepository;
import com.elevata.gsrtc.service.BookingManagementService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class BookingManagementServiceImpl implements BookingManagementService {
    private final BookingRepository bookingRepository;
    private final TripRepository tripRepository;
    private final RouteStopsRepository routeStopsRepository;
    private final PassengerRepository passengerRepository;

    @Autowired
    public BookingManagementServiceImpl(BookingRepository bookingRepository, TripRepository tripRepository, RouteStopsRepository routeStopsRepository, PassengerRepository passengerRepository) {
        this.bookingRepository = bookingRepository;
        this.tripRepository = tripRepository;
        this.routeStopsRepository = routeStopsRepository;
        this.passengerRepository = passengerRepository;
    }

    @Override
    public BookingManagementTripDTO filterByTripCode(
            int depotId, String tripCode
    ) {
        if (!tripCode.matches("^\\d{4}[A-Z]{3}[A-Z]{3}[A-Z]{2}\\d{2}$")) {
            throw new InvalidTripCodeException("Invalid trip code format: " + tripCode);
        }

        Trip trip = tripRepository.findByTripCode(tripCode);
        if (trip.getBusDepot().getDepotId() != depotId) {
            throw new TripNotFoundException("Trip not found for depot");
        }

        BookingManagementTripDTO bookingBusTripDTO = makeBookingOverviewDTO(trip);

        return bookingBusTripDTO;
    }


    //Add Error handling
    @Override
    public List<BookingManagementTripDTO> filterByRouteAndClassAndRevenue(
            int depotId, String route, String classType, BigDecimal revenue
    ) {
        //Filter by route, class and revenue
        return tripRepository.findAllByRoute_RouteName(route)
                .stream()
                .filter(trip -> {
                    BigDecimal totalRevenue = bookingRepository.countTotalRevenueByTripCode(trip.getTripCode());

                    return trip.getBus().getBusType().equalsIgnoreCase(classType)
                            && totalRevenue.equals(revenue);
                })
                .map(this::makeBookingOverviewDTO)
                .toList();
    }

    @Override
    public List<BookingManagementTripDTO> findAll(int depotId) {
        return tripRepository
                .findAllByBusDepotDepotId(depotId)
                .stream()
                .map(this::makeBookingOverviewDTO)
                .toList();
    }

    private BookingManagementTripDTO makeBookingOverviewDTO(Trip trip) {
        String tripCode = trip.getTripCode();
        List<String> stops = routeStopsRepository
                .findByBusRouteRouteId(trip.getRoute().getRouteId()).stream()
                .map(RouteStops::getStopName).toList();

        //Fetch booked seats and remaining seats
        int booked = passengerRepository.countPassengerByTripCode(tripCode);
        int remaining = trip.getBus().getBusLayout().getTotalSeats() - booked;

        //Calculate total revenue;
        BigDecimal revenue = bookingRepository.countTotalRevenueByTripCode(tripCode);
        if (revenue == null) {
            revenue = BigDecimal.valueOf(0);
        }

        return new BookingManagementTripDTO(
                trip.getBus().getBusNumber(),
                trip.getRoute().getRouteName(),
                stops,
                trip.getBus().getBusType(),
                trip.getTripDate(),
                trip.getTripCode(),
                booked,
                remaining,
                revenue
        );
    }

    @Override
    public List<BookingManagementTripDTO> findAllByDate(int depotId, LocalDate date) {

        //Use DB level filtering !
        List<BookingManagementTripDTO> result =
                tripRepository.findAllByBusDepotDepotId(depotId)
                        .stream()
                        .filter(trip -> trip.getTripDate().equals(date))
                        .map(this::makeBookingOverviewDTO)
                        .toList();
        System.out.println(result);

        if (result.isEmpty()) {
            throw new TripNotFoundException("No trips found for date: " + date);
        }

        return result;
    }
}
