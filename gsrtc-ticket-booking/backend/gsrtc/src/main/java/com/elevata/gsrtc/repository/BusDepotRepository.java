package com.elevata.gsrtc.repository;

import com.elevata.gsrtc.dto.BusDepotSummaryDTO;
import com.elevata.gsrtc.dto.DepotDTO;
import com.elevata.gsrtc.dto.DepotDropDownDTO;
import com.elevata.gsrtc.entity.BusDepot;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BusDepotRepository extends JpaRepository<BusDepot, Integer> {
    @Query("SELECT depotName FROM BusDepot")
    List<String> findAllDepotNames();

    Optional<BusDepot> findByEmail(String email);

    @Query("""
            SELECT new com.elevata.gsrtc.dto.DepotDropDownDTO(
                d.depotId,
                d.depotName
            ) FROM BusDepot d
    """)
    List<DepotDropDownDTO> getDepots();

    @Query("""
            SELECT new com.elevata.gsrtc.dto.DepotDTO(
                d.depotId, d.depotName, d.city, d.email, d.address)
            FROM BusDepot d
            WHERE d.depotName = :depotName
            """)
    Optional<DepotDTO> findByDepotName(@Param("depotName")String depotName);

    @Query("""
            SELECT new com.elevata.gsrtc.dto.DepotDTO(
                d.depotId, d.depotName, d.city, d.email, d.address)
            FROM BusDepot d
    """)
    Page<DepotDTO> getList(Pageable pageable);

    @Query("""
            SELECT new com.elevata.gsrtc.dto.BusDepotSummaryDTO(
                d.depotName,
                d.address,
                COUNT(DISTINCT b.busId),
                COALESCE(SUM(book.totalFare),0),
                COUNT(DISTINCT s.staffId),
                COUNT(DISTINCT book.bookingId)
            )
            FROM BusDepot d
            LEFT JOIN Bus b ON b.busDepot.depotId = d.depotId
            LEFT JOIN Trip t ON t.busDepot.depotId = d.depotId
            LEFT JOIN Booking book ON book.trip.tripId = t.tripId
            LEFT JOIN Staff s ON s.busDepot.depotId = d.depotId
            GROUP BY d.depotId
    """)
    Page<BusDepotSummaryDTO> findDepotSummary(Pageable pageable);

    @Query("""
        SELECT new com.elevata.gsrtc.dto.BusDepotSummaryDTO(
            d.depotName,
            d.address,
            COUNT(DISTINCT b.busId),
            COALESCE(SUM(book.totalFare),0),
            COUNT(DISTINCT s.staffId),
            COUNT(DISTINCT book.bookingId)
        )
        FROM BusDepot d
        LEFT JOIN Bus b ON b.busDepot.depotId = d.depotId
        LEFT JOIN Trip t ON t.busDepot.depotId = d.depotId
        LEFT JOIN Booking book ON book.trip.tripId = t.tripId
        LEFT JOIN Staff s ON s.busDepot.depotId = d.depotId
        WHERE LOWER(d.depotName) = LOWER(:depotName)
        GROUP BY d.depotId
    """)
    Optional<BusDepotSummaryDTO> findDepotSummaryByDepotName(String depotName);

    long count();
}
