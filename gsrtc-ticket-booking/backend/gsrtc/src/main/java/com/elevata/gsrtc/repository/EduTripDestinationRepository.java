package com.elevata.gsrtc.repository;

import com.elevata.gsrtc.entity.EducationalTripDestination;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EduTripDestinationRepository
        extends JpaRepository<EducationalTripDestination, Integer> {

//    String findByLocationType(String locationType);

    @Query("""
            SELECT d.destinationName
            FROM EducationalTripDestination d
            JOIN d.tripRequest tr
            WHERE tr.requestId = :requestId
                AND d.locationType = :locationType
            """)
    String findDestinationName(
            @Param("requestId") int requestId,
            @Param("locationType") String locationType);

    @Query("""
            SELECT d.destinationName
            FROM EducationalTripDestination d
            JOIN d.tripRequest tr
            WHERE tr.requestId = :requestId
                AND d.locationType = :locationType
            ORDER BY d.sequenceNo ASC
        """)
    List<String> findLocationsByIdAndType(
            @Param("requestId") int requestId,
            @Param("locationType") String locationType);
}
