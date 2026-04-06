package com.elevata.gsrtc.repository;

import com.elevata.gsrtc.entity.EducationalTripBooking;
import com.elevata.gsrtc.enums.BookingStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface EducationalTripBookingRepository
        extends JpaRepository<EducationalTripBooking, Integer> {

    Optional<List<EducationalTripBooking>> findByTripRequestRequestId(Integer requestId);

//    Optional<EducationalTripBooking> findByPnr(String pnr);

    List<EducationalTripBooking> findByBookingStatus(BookingStatus status);

    boolean existsByPnr(String pnr);

    long count();

    @Query("""
        SELECT COALESCE(SUM(e.totalFare),0)
        FROM EducationalTripBooking e
    """)
    double totalEducationalTripRevenue();
}
