package com.elevata.gsrtc.repository;

import com.elevata.gsrtc.dto.GraphDTO;
import com.elevata.gsrtc.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Integer> {
    Booking findByPnr(String pnr);
    List<Booking> findAllByTripTripCode(String tripCode);
    List<Booking> findAllByAppUserUserId(Long userId);

    @Query("""
            SELECT SUM(b.totalFare)
            FROM Booking b
            WHERE b.trip.tripCode = :tripCode
            """)
    BigDecimal countTotalRevenueByTripCode(String tripCode);

    long count();

    @Query("""
        SELECT COUNT(b)
        FROM Booking b
        WHERE MONTH(b.bookingTime) = :month
        AND YEAR(b.bookingTime) = :year
    """)
    long countBookingsByMonth(@Param("month") int month, @Param("year") int year);

    @Query("""
        SELECT COALESCE(SUM(b.totalFare),0)
        FROM Booking b
        WHERE MONTH(b.bookingTime) = :month
        AND YEAR(b.bookingTime) = :year
    """)
    double getRevenueByMonth(@Param("month") int month, @Param("year") int year);

//    @Query("""
//        SELECT new com.elevata.gsrtc.dto.BookingTrendDTO(
//            FUNCTION('MONTHNAME', b.bookingTime),
//            COUNT(b)
//        )
//        FROM Booking b
//        GROUP BY MONTH(b.bookingTime)
//        ORDER BY MONTH(b.bookingTime)
//    """)
//    List<BookingTrendDTO> getBookingTrends();

    @Query("""
        SELECT new com.elevata.gsrtc.dto.GraphDTO(
            MONTH(b.bookingTime),
            COUNT(b)
        )
        FROM Booking b
        WHERE YEAR(b.bookingTime) = :year
        GROUP BY MONTH(b.bookingTime)
        ORDER BY MONTH(b.bookingTime)
    """)
    List<GraphDTO> getMonthlyBookingTrends(@Param("year") int year);

    @Query("""
        SELECT new com.elevata.gsrtc.dto.GraphDTO(
            MONTH(b.bookingTime),
            COALESCE(SUM(b.totalFare),0)
        )
        FROM Booking b
        WHERE YEAR(b.bookingTime) = :year
        GROUP BY MONTH(b.bookingTime)
        ORDER BY MONTH(b.bookingTime)
    """)
    List<GraphDTO> getMonthlyRevenueTrends(@Param("year") int year);

//    @Query("SELECT COUNT(b) FROM Booking b")
//    Long countAllBookings();

//    @Query("""
//        SELECT new com.elevata.gsrtc.dto.WeeklyBookingRow(
//            FUNCTION('DAYNAME_FN', b.createdAt),
//            COUNT(b)
//        )
//        FROM Booking b
//        WHERE b.createdAt >= :startDate
//        GROUP BY FUNCTION('DAYOFWEEK', b.createdAt)
//        ORDER BY FUNCTION('DAYOFWEEK', b.createdAt)
//    """)
//    List<WeeklyBookingRow> getWeeklyBookingCounts(
//            @Param("startDate") LocalDateTime startDate
//    );
}
