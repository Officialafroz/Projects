package com.elevata.gsrtc.service.impl;

import com.elevata.gsrtc.dto.EduPaymentDTO;
import com.elevata.gsrtc.entity.*;
import com.elevata.gsrtc.enums.BookingStatus;
import com.elevata.gsrtc.enums.PaymentMethod;
import com.elevata.gsrtc.enums.PaymentStatus;
import com.elevata.gsrtc.repository.BookingRepository;
import com.elevata.gsrtc.repository.EduPaymentRepository;
import com.elevata.gsrtc.repository.EducationalTripBookingRepository;
import com.elevata.gsrtc.repository.PaymentRepository;
import com.elevata.gsrtc.service.PaymentService;
import com.elevata.gsrtc.service.ReferenceNumberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PaymentServiceImpl implements PaymentService {
    private final PaymentRepository paymentRepository;
    private final EduPaymentRepository eduPaymentRepository;
    private final BookingRepository bookingRepository;
    private final EducationalTripBookingRepository eduTripBookingRepository;

    @Autowired
    public PaymentServiceImpl(PaymentRepository paymentRepository, EduPaymentRepository eduPaymentRepository, BookingRepository bookingRepository, EducationalTripBookingRepository eduTripBookingRepository) {
        this.paymentRepository = paymentRepository;
        this.eduPaymentRepository = eduPaymentRepository;
        this.bookingRepository = bookingRepository;
        this.eduTripBookingRepository = eduTripBookingRepository;
    }

    @Transactional
    @Override
    public void pay(EduPaymentDTO dto) {
        System.out.println("-".repeat(40) + " Entering method.");
        List<EducationalTripBooking> booking = null;

        try {
            booking = eduTripBookingRepository
                    .findByTripRequestRequestId(dto.getRequestId())
                    .orElseThrow();

            System.out.println(booking);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }

        System.out.println("-".repeat(40) + "After booking fetch.");

        boolean hasPayed = eduPaymentRepository
                .findAllByEducationalTripBookingBookingId(dto.getRequestId()) != null;

        if (!hasPayed) {
            System.out.println("-".repeat(50) + "inside if -----");
            EducationalTripRequest request = booking.getFirst().getTripRequest();

            LocalDateTime baseTime = request.getUpdatedAt() != null
                    ? request.getUpdatedAt()
                    : request.getCreatedAt();

            if (baseTime.plusHours(72).isBefore(LocalDateTime.now())) {
                throw new RuntimeException("Advance payment window (72 hours) has expired");
            }

            BigDecimal requiredAdvance = request.getBudget()
                    .multiply(BigDecimal.valueOf(0.5))
                    .setScale(2, RoundingMode.HALF_UP);

//            System.out.println("-".repeat(40) + "advance : " + requiredAdvance);
            System.out.println("-".repeat(40) + "user payment : " + dto.getAmount());

            if (dto.getAmount().compareTo(requiredAdvance) < 0) {
                throw new RuntimeException("Insufficient advance payment");
            }
        }

        EduPayment payment = makePayment(dto, booking.getFirst());

        eduPaymentRepository.save(payment);

        booking.getFirst().setBookingStatus(BookingStatus.CONFIRMED);

        eduTripBookingRepository.save(booking.getFirst()    );
    }

    @Override
    @Transactional
    public String save(double totalAmount, String pnr) {
        Booking existingBooking = bookingRepository.findByPnr(pnr);
        Payment payment = new Payment();
        payment.setAmount(totalAmount);
        payment.setPaymentMethod("UPI");
        payment.setStatus("PAID");
        payment.setTransactionRef(ReferenceNumberService.generateTransRef());
        payment.setPaymentDate(LocalDateTime.now());
        payment.setBooking(existingBooking);

        paymentRepository.save(payment);
        System.out.println("Payment saved successfully for " + existingBooking.getAppUser().getName());

        return "Payment saved successfully.";
    }

    @Override
    public List<Payment> findAll() {
        return paymentRepository.findAll();
    }

    @Override
    public Payment findById(int paymentId) {
        Optional<Payment> paymentOptional = paymentRepository.findById(paymentId);

        if (paymentOptional.isEmpty()) {
            throw new RuntimeException("Payment not found for id : " + paymentId);
        }

        return paymentOptional.get();
    }

    @Transactional
    @Override
    public void save(Payment payment) {
        if (payment.getBooking() != null && payment.getBooking().getBookingId() > 0) {
            int bookingId = payment.getBooking().getBookingId();

            Optional<Booking> existingBooking = bookingRepository.findById(bookingId);
            if (existingBooking.isEmpty()) {
                throw new RuntimeException("Invalid booking id - " + bookingId);
            }

            // Attach the managed booking entity
            payment.setBooking(existingBooking.get());
        } else {
            throw new RuntimeException("Booking information missing or invalid.");
        }

        // Now save payment
        paymentRepository.save(payment);
        System.out.println("Payment saved.");
    }

    @Transactional
    @Override
    public void delete(int paymentId) {
        paymentRepository.deleteById(paymentId);
        System.out.println("Payment safely deleted.");
    }

    private EduPayment makePayment(EduPaymentDTO dto, EducationalTripBooking booking) {
        EduPayment payment = new EduPayment();

        payment.setEducationalTripBooking(booking);
        payment.setAmount(dto.getAmount());
        payment.setMethod(switch (dto.getMethod().toUpperCase()) {
            case "CASH" -> PaymentMethod.CASH;
            case "CARD" -> PaymentMethod.CARD;
            case "UPI" -> PaymentMethod.UPI;
            case "NETBANKING" -> PaymentMethod.NETBANKING;
            default -> throw new RuntimeException("Payment method not supported.");
        });
        payment.setStatus(PaymentStatus.PAID);
        payment.setTransactionRef(ReferenceNumberService.generateTransRef());

        return payment;
    }
}
