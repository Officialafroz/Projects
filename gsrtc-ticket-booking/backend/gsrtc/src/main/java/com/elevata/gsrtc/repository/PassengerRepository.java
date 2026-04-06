package com.elevata.gsrtc.repository;

import com.elevata.gsrtc.dto.PassengerManagementDTO;
import com.elevata.gsrtc.dto.GraphDTO;
import com.elevata.gsrtc.entity.Passenger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface PassengerRepository extends JpaRepository<Passenger, Integer> {
    List<Passenger> findAllByBookingBookingId(int bookingId);
    Optional<Passenger> findByPassRef(String passRef);

    @Query("""
            SELECT COUNT(p) FROM Passenger p JOIN p.booking b JOIN b.trip t
            WHERE t.tripCode = :tripCode
    """)
    int countPassengerByTripCode(@Param("tripCode") String tripCode);

    @Query("""
            SELECT new com.elevata.gsrtc.dto.PassengerManagementDTO(
                p.passRef, p.name, p.age, p.gender, b.bookingTime, p.boardingPoint,
                p.destinationPoint, p.seatNo, p.individualFare)
            FROM Passenger p
            JOIN p.booking b JOIN b.trip t JOIN t.busDepot d
            WHERE d.depotId = :depotId
    """)
    List<PassengerManagementDTO> findAllByDepotId(@Param("depotId") Integer depotId);

    @Query("""
            SELECT new com.elevata.gsrtc.dto.PassengerManagementDTO(
                p.passRef, p.name, p.age, p.gender, b.bookingTime, p.boardingPoint,
                p.destinationPoint, p.seatNo, p.individualFare)
            FROM Passenger p
            JOIN p.booking b
            WHERE b.pnr = :pnr OR p.passRef = :passRef
    """)
    List<PassengerManagementDTO> findAllByPnrOrPassengerReference(
            @Param("pnr") String pnr,@Param("passRef") String passRef);

    @Query("""
            SELECT new com.elevata.gsrtc.dto.PassengerManagementDTO(
                p.passRef, p.name, p.age, p.gender, b.bookingTime, p.boardingPoint,
                p.destinationPoint, p.seatNo, p.individualFare)
            FROM Passenger p
            JOIN p.booking b JOIN b.trip t JOIN t.bus tripBus
            WHERE (:busNumber IS NULL OR tripBus.busNumber = :busNumber)
            AND (:tripCode IS NULL OR t.tripCode = :tripCode)
            AND (:totalSeats IS NULL
                OR (SELECT COUNT(p2)
                    FROM Passenger p2
                    JOIN p2.booking b2
                    WHERE b2.trip = t
                ) = :totalSeats
        )""")
    List<PassengerManagementDTO> findAllByBusNumberOrTripCodeOrTotalSeats(
            @Param("busNumber") String busNumber,
            @Param("tripCode") String tripCode,
            @Param("totalSeats") Integer totalSeats);

//    @Query("""
//    SELECT new com.elevata.gsrtc.dto.PassengerManagementDTO(
//        p.passRef,
//        p.name,
//        p.age,
//        p.gender,
//        b.bookingTime,
//        p.boardingPoint,
//        p.destinationPoint,
//        p.seatNo,
//        p.individualFare
//    )
//    FROM Passenger p
//    JOIN p.booking b
//    JOIN b.trip t
//    JOIN t.bus tripBus
//    JOIN t.route r
//    WHERE (:route IS NULL OR r.routeName = :route)
//      AND (:busClass IS NULL OR tripBus.busType = :busClass)
//      AND (
//          :revenue IS NULL OR
//          (
//              SELECT SUM(b2.totalFare)
//              FROM Booking b2
//              WHERE b2.trip = t
//          ) >= :revenue
//      )
//""")
//    List<PassengerManagementDTO> findPassengers(
//            @Param("route") String route,
//            @Param("busClass") String busClass,
//            @Param("revenue") BigDecimal revenue
//    );

    @Query("""
        SELECT new com.elevata.gsrtc.dto.PassengerManagementDTO(
            p.passRef, p.name, p.age, p.gender, b.bookingTime, p.boardingPoint,
            p.destinationPoint, p.seatNo, p.individualFare)
        FROM Passenger p
        JOIN p.booking b
        JOIN b.trip t
        JOIN t.bus tripBus
        JOIN t.route r
        WHERE (:route IS NULL OR r.routeName = :route)
        AND (:busClass IS NULL OR tripBus.busType = :busClass)
        AND (
            :revenue IS NULL
            OR (
               SELECT SUM(b2.totalFare)
               FROM Booking b2
               WHERE b2.trip = t
            ) >= :revenue
        )
    """)
    //Change the name of the method and bus class parameter
    List<PassengerManagementDTO> findPassengers(
            @Param("route") String route,
            @Param("busClass") String busClass,
            @Param("revenue") BigDecimal revenue
    );

    @Query("""
        SELECT COUNT(p)
        FROM Passenger p
        JOIN p.booking b
        WHERE MONTH(b.bookingTime) = :month
        AND YEAR(b.bookingTime) = :year
    """)
    long totalSeatsBookedByMonth(@Param("month") int month, @Param("year") int year);

    @Query("""
        SELECT new com.elevata.gsrtc.dto.GraphDTO(
            MONTH(b.bookingTime),
            COUNT(p)
        )
        FROM Passenger p
        JOIN p.booking b
        WHERE YEAR(b.bookingTime) = :year
        GROUP BY MONTH(b.bookingTime)
        ORDER BY MONTH(b.bookingTime)
    """)
    List<GraphDTO> getSeatOccupancy(int year);

}
