package com.elevata.gsrtc.service.impl;

import com.elevata.gsrtc.dto.TicketDTO;
import com.elevata.gsrtc.entity.Booking;
import com.elevata.gsrtc.entity.Passenger;
import com.elevata.gsrtc.entity.Trip;
import com.elevata.gsrtc.enums.BookingStatus;
import com.elevata.gsrtc.repository.BookingRepository;
import com.elevata.gsrtc.repository.PassengerRepository;
import com.elevata.gsrtc.repository.AppUserRepository;
import com.elevata.gsrtc.service.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class TicketServiceImpl implements TicketService {
    private PassengerRepository passengerRepository;
    private AppUserRepository appUserRepository;
    private BookingRepository bookingRepository;

    @Autowired
    public TicketServiceImpl(PassengerRepository passengerRepository, AppUserRepository appUserRepository, BookingRepository bookingRepository) {
        this.passengerRepository = passengerRepository;
        this.appUserRepository = appUserRepository;
        this.bookingRepository = bookingRepository;
    }

    @Override
    public List<TicketDTO> getTickets(String email) {
        List<TicketDTO> tickets = null;
        long userId = appUserRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found.")).getUserId();
        List<Booking> userBooking = bookingRepository.findAllByAppUserUserId(userId);
//        System.out.println(userBooking);

        if (userBooking != null){
            tickets = new ArrayList<>();
            for (Booking booking : userBooking) {
                if (!booking.getStatus().equals(BookingStatus.CANCELLED.name())) {
                    TicketDTO ticketDTO = new TicketDTO();
                    ticketDTO.setTripCode(booking.getTrip().getTripCode());
                    ticketDTO.setPnr(booking.getPnr());
                    ticketDTO.setRoute(booking.getTrip().getRoute().getRouteName());
                    ticketDTO.setJourneyDate(booking.getJourneyDate());
                    ticketDTO.setClassType(booking.getTrip().getRoute().getClassType());

                    List<Passenger> passengers = passengerRepository.findAllByBookingBookingId(booking.getBookingId());
                    List<String> passengerNames = passengers.stream()
                            .map(Passenger::getName)
                            .toList();
                    ticketDTO.setPassengers(passengerNames);

                    Set<Integer> seats = passengers.stream()
                            .map(Passenger::getSeatNo)
                            .collect(Collectors.toSet());
                    ticketDTO.setSeats(seats);

                    double totalFare = passengers.stream()
                            .mapToDouble(Passenger::getIndividualFare)
                            .sum();

                    ticketDTO.setTotalFare(totalFare);

                    tickets.add(ticketDTO);
                }

            }
        }

        return tickets;
    }

    @Override
    public TicketDTO getTicket(String pnr) {
        Booking existingBooking = bookingRepository.findByPnr(pnr);
        if (!existingBooking.getStatus().equalsIgnoreCase("cancelled")) {
            List<Passenger> passengers = passengerRepository
                    .findAllByBookingBookingId(existingBooking.getBookingId());
            Trip existingTrip = existingBooking.getTrip();
            TicketDTO ticketDTO = new TicketDTO();
            ticketDTO.setTripCode(existingTrip.getTripCode());
            ticketDTO.setRoute(existingTrip.getRoute().getRouteName());
            ticketDTO.setJourneyDate(existingBooking.getJourneyDate());
            ticketDTO.setClassType(existingTrip.getRoute().getClassType());

            List<String> passengerNames = passengers.stream()
                    .map(Passenger::getName).toList();
            ticketDTO.setPassengers(passengerNames);

            Set<Integer> seats = passengers.stream()
                    .map(Passenger::getSeatNo).collect(Collectors.toSet());
            ticketDTO.setSeats(seats);

            double totalFare = passengers.stream()
                    .mapToDouble(Passenger::getIndividualFare)
                    .sum();
            ticketDTO.setTotalFare(totalFare);
            return ticketDTO;
        }
        return null;
    }
}
