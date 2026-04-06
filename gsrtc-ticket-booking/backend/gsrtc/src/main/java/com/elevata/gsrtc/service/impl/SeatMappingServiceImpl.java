package com.elevata.gsrtc.service.impl;

import com.elevata.gsrtc.entity.Booking;
import com.elevata.gsrtc.entity.Passenger;
import com.elevata.gsrtc.repository.BookingRepository;
import com.elevata.gsrtc.repository.PassengerRepository;
import com.elevata.gsrtc.repository.TripRepository;
import com.elevata.gsrtc.service.SeatMappingService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class SeatMappingServiceImpl implements SeatMappingService {
    private final TripRepository tripRepository;
    private final PassengerRepository passengerRepository;
    private final BookingRepository bookingRepository;

    @Autowired
    public SeatMappingServiceImpl(TripRepository tripRepository, PassengerRepository passengerRepository, BookingRepository bookingRepository) {
        this.tripRepository = tripRepository;
        this.passengerRepository = passengerRepository;
        this.bookingRepository = bookingRepository;
    }

    @Override
    public Set<Integer> mapBookedSeats(String tripCode) {
        List<Booking> bookings = bookingRepository.findAllByTripTripCode(tripCode);
        Set<Integer> seats = new HashSet<>();

        if (bookings != null) {
            for (Booking booking : bookings) {
                List<Passenger> passengers = passengerRepository
                        .findAllByBookingBookingId(booking.getBookingId());

                for (Passenger passenger : passengers) {
                    seats.add(passenger.getSeatNo());  // Set ignores duplicates automatically
                }
            }
        }

        System.out.println(bookings);
        System.out.println(seats);

        return seats;
    }

    @Override
    public int findTotalSeats(String tripCode) {
        return tripRepository.findByTripCode(tripCode).getBus().getBusLayout().getTotalSeats();
    }
}
