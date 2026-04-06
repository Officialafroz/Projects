package com.elevata.gsrtc.service.impl;

import com.elevata.gsrtc.dto.UserBookingDTO;
import com.elevata.gsrtc.entity.AppUser;
import com.elevata.gsrtc.entity.Passenger;
import com.elevata.gsrtc.enums.BookingStatus;
import com.elevata.gsrtc.repository.AppUserRepository;
import com.elevata.gsrtc.repository.BookingRepository;
import com.elevata.gsrtc.entity.Booking;
import com.elevata.gsrtc.entity.Trip;
import com.elevata.gsrtc.repository.PassengerRepository;
import com.elevata.gsrtc.repository.TripRepository;
import com.elevata.gsrtc.service.BookingService;
import com.elevata.gsrtc.service.ReferenceNumberService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class BookingServiceImpl implements BookingService {
    private final BookingRepository bookingRepository;
    private final PassengerRepository passengerRepository;
    private final TripRepository tripRepository;
    private final AppUserRepository userRepository;

    @Autowired
    public BookingServiceImpl(BookingRepository bookingRepository, PassengerRepository passengerRepository, TripRepository tripRepository, AppUserRepository userRepository) {
        this.bookingRepository = bookingRepository;
        this.passengerRepository = passengerRepository;
        this.tripRepository = tripRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<Booking> findAll() {
        return bookingRepository.findAll();
    }

    @Override
    public Booking findById(int bookingId) {
        Optional<Booking> optionalBooking = bookingRepository.findById(bookingId);

        if (optionalBooking.isPresent()) {
            return optionalBooking.get();
        } else {
            throw new RuntimeException("Booking not found for id: " + bookingId);
        }
    }

    @Override
    @Transactional
    public void save(Booking booking) {
        if (booking.getAppUser() != null && booking.getTrip() != null &&
                booking.getAppUser().getUserId() > 0 && booking.getTrip().getTripId() > 0
        ) {
            long userId = booking.getAppUser().getUserId();
            int tripId = booking.getTrip().getTripId();


            Optional<AppUser> existingAppUser = userRepository.findById(userId);
            Optional<Trip> existingTrip = tripRepository.findById(tripId);

            if (existingAppUser.isEmpty() || existingTrip.isEmpty()) {
                throw new RuntimeException(
                        String.format("Invalid user id - %d or bus id - %d", userId, tripId));
            }
            booking.setAppUser(existingAppUser.get());
            booking.setTrip(existingTrip.get());
        }

        bookingRepository.save(booking);
        System.out.println("Booking is successfully created.");
    }

    @Override
    public String save(UserBookingDTO bookingDTO) {
        AppUser appUser = userRepository.findByEmail(bookingDTO.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found."));
        Trip trip = tripRepository.findByTripCode(bookingDTO.getTripCode());
        String pnr = ReferenceNumberService.generatePNR();

        Booking newBooking = new Booking();
        newBooking.setAppUser(appUser);
        newBooking.setBookingTime(LocalDateTime.now());
        newBooking.setTrip(trip);
        newBooking.setPnr(pnr);
        newBooking.setStatus(BookingStatus.CONFIRMED.name());
        newBooking.setJourneyDate(bookingDTO.getJourneyDate());
        newBooking.setTotalFare(bookingDTO.getTotalFare());

        bookingRepository.save(newBooking);
        System.out.println("Booking saved successfully for " + appUser.getName() + ".");
        return pnr;
    }

    @Transactional
    @Override
    public String cancelBookingByPnr(String pnr) {
        Booking booking = bookingRepository.findByPnr(pnr);

        if (booking != null) {
            List<Passenger> passengers = passengerRepository.findAllByBookingBookingId(booking.getBookingId());

            booking.setStatus("CANCELLED");
            passengers.forEach(passenger -> passenger.setStatus("CANCELLED"));

            passengerRepository.saveAll(passengers);
            bookingRepository.save(booking);
            return "Booking cancelled successfully for PNR: " + pnr;
        } else {
            return "Booking not found for PNR: " + pnr;
        }
    }

    @Transactional
    @Override
    public String deletePassengerByPassRef(String passRef) {
        Optional<Passenger> passengerOpt = passengerRepository.findByPassRef(passRef);

        if (passengerOpt.isPresent()) {
            Passenger passenger = passengerOpt.get();
            passenger.setStatus("CANCELLED");

            passengerRepository.save(passenger);
            return "Passenger booking cancelled successfully with reference: " + passRef;
        } else {
            return "Passenger not found with reference: " + passRef;
        }
    }

    @Override
    @Transactional
    public void delete(int bookingId) {
        bookingRepository.deleteById(bookingId);
        System.out.println("Booking is safely deleted.");
    }
}
