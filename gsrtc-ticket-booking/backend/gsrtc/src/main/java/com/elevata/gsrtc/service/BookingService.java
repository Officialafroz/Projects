package com.elevata.gsrtc.service;

import com.elevata.gsrtc.dto.UserBookingDTO;
import com.elevata.gsrtc.entity.Booking;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BookingService {
    List<Booking> findAll();

    Booking findById(int bookingId);

    String save(UserBookingDTO bookingDTO);

    void save(Booking booking);

    String cancelBookingByPnr(String pnr);

    void delete(int bookingId);

    String deletePassengerByPassRef(String passRef);
}
