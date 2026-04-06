package com.elevata.gsrtc.repository;

import com.elevata.gsrtc.entity.EduPayment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EduPaymentRepository extends JpaRepository<EduPayment, Integer> {

//    @Query(
//        """
//            SELECT p
//            FROM EduPayment p
//            WHERE p.educationalTripBooking.bookingId = :bookingId
//        """
//    )
    List<EduPayment> findAllByEducationalTripBookingBookingId(Integer bookingId);
}
