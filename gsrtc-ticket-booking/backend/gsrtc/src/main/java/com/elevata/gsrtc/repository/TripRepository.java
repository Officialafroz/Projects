package com.elevata.gsrtc.repository;

import com.elevata.gsrtc.entity.Trip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TripRepository extends JpaRepository<Trip, Integer> {
    List<Trip> findAllByBusDepotDepotId(int depotId);
    List<Trip> findAllByTripDate(LocalDate tripDate);
    Trip findByTripCode(String tripCode);
    List<Trip> findAllByRoute_RouteName(String route);

    long count();

    @Query("""
        SELECT COUNT(t)
        FROM Trip t
        WHERE MONTH(t.departureTime) = :month
        AND YEAR(t.departureTime) = :year
    """)
    long countTripsByMonth(@Param("month") int month, @Param("year") int year);
}
