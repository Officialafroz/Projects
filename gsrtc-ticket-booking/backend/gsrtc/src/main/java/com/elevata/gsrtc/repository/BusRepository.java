package com.elevata.gsrtc.repository;

import com.elevata.gsrtc.entity.Bus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BusRepository extends JpaRepository<Bus, Integer> {
    Bus findByBusNumber(String busNumber);
    List<Bus> findAllByBusDepotDepotId(Integer depotId);

    long count();

    @Query("""
        SELECT COUNT(b)
        FROM Bus b
        JOIN Trip t ON t.bus.busId = b.busId
        WHERE t.status = 'ACTIVE'
        AND MONTH(t.departureTime) = :month
        AND YEAR(t.departureTime) = :year
    """)
    long countOnTripBuses(@Param("month") int month, @Param("year") int year);

//    @Query("SELECT COUNT(b) FROM Bus b WHERE b.status = 'ACTIVE'")
//    Integer countActiveBuses();
}
