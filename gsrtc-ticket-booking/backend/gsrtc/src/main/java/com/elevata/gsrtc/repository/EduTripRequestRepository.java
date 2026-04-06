package com.elevata.gsrtc.repository;

import com.elevata.gsrtc.dto.TripRequestDetailDTO;
import com.elevata.gsrtc.dto.TripRequestOverviewDTO;
import com.elevata.gsrtc.entity.EducationalTripRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EduTripRequestRepository
        extends JpaRepository<EducationalTripRequest, Integer> {

    Optional<List<EducationalTripRequest>> findByAppUserUserId(Integer userId);

    @Query("""
            SELECT new com.elevata.gsrtc.dto.TripRequestOverviewDTO(
                tr.requestId,
                i.instituteName,
                tr.tripName,
                td.destinationName,
                tr.tripDays,
                tr.budget,
                tr.tripStartDatetime,
                tr.status
            )
            FROM EducationalTripRequest tr
            JOIN tr.institute i
            JOIN EducationalTripDestination td
                ON td.tripRequest.requestId = tr.requestId
            WHERE td.locationType = :locationType AND tr.busDepot.depotId = :depotId
            ORDER BY tr.tripStartDatetime DESC
        """)
    List<TripRequestOverviewDTO> findAllNewestFirst(
            @Param("depotId") int depotId,
            @Param("locationType") String locationType);
}
