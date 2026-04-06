package com.elevata.gsrtc.controller;

import com.elevata.gsrtc.dto.UserBookingDTO;
import com.elevata.gsrtc.service.BookingService;
import com.elevata.gsrtc.service.impl.BookingServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/end-user/booking")
public class BookingController {
    private final BookingService bookingService;

    @Autowired
    public BookingController(BookingServiceImpl bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping
    public String save(@RequestBody UserBookingDTO bookingDTO) {
        return bookingService.save(bookingDTO);
    }

    @DeleteMapping("/cancel/{reference}")
    public ResponseEntity<String> cancelBooking(@PathVariable String reference) {
        String response;
        if (reference.length() == 10) {
            response = bookingService.cancelBookingByPnr(reference);
        } else {
            response = bookingService.deletePassengerByPassRef(reference);
        }
        return ResponseEntity.ok(response);
    }

//    @GetMapping("/bookings")
//    public List<Booking> findAll() {
//        return bookingService.findAll();
//    }
//
//    @GetMapping("/{bookingId}")
//    public Booking findById(@PathVariable int bookingId) {
//        return bookingService.findById(bookingId);
//    }
//
//    @PostMapping("/add")
//    public void add(@RequestBody Booking booking) {
//        bookingService.save(booking);
//    }
//
//    @DeleteMapping("/delete/{BookingId}")
//    public void delete(@PathVariable int bookingId) {
//        bookingService.delete(bookingId);
//    }
}
