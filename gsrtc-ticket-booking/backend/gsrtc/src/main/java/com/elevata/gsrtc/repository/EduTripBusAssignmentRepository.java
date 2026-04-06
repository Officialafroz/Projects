package com.elevata.gsrtc.repository;

import com.elevata.gsrtc.entity.EducationalTripBusAssignment;
import com.elevata.gsrtc.enums.BusAssignmentStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EduTripBusAssignmentRepository
        extends JpaRepository<EducationalTripBusAssignment, Integer> {

    List<EducationalTripBusAssignment> findByTripBooking_bookingId(
            Integer bookingId);

    List<EducationalTripBusAssignment> findByStatus(BusAssignmentStatus status);

    List<EducationalTripBusAssignment> findByBus_BusId(Integer busId);
}
