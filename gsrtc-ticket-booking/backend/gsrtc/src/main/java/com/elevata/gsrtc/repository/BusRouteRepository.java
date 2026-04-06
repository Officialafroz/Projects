package com.elevata.gsrtc.repository;

import com.elevata.gsrtc.dto.GraphDTO;
import com.elevata.gsrtc.entity.BusRoute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BusRouteRepository extends JpaRepository<BusRoute, Integer> {
    List<BusRoute> findAllByBusDepotDepotId(int depotId);
    BusRoute findById(int routeId);

    long count();

    @Query("""
        SELECT COUNT(r)
        FROM BusRoute r
    """)
    long countRoutesByMonth();
//        WHERE MONTH(r.createdAt) = :month
//        AND YEAR(r.createdAt) = :year

//    @Query("""
//        SELECT new com.elevata.gsrtc.dto.GraphDTO(
//            r.routeName,
//            COUNT(b)
//        )
//        FROM Booking b
//        JOIN b.trip t
//        JOIN t.route r
//        GROUP BY r.routeName
//        ORDER BY COUNT(b) DESC
//    """)
//    List<GraphDTO> getRoutePopularity();
}
