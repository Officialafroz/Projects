package com.elevata.gsrtc.service.impl;

import com.elevata.gsrtc.entity.Bus;
import com.elevata.gsrtc.entity.EducationalTripBooking;
import com.elevata.gsrtc.entity.EducationalTripBusAssignment;
import com.elevata.gsrtc.entity.Staff;
import com.elevata.gsrtc.enums.BusAssignmentStatus;
import com.elevata.gsrtc.repository.BusRepository;
import com.elevata.gsrtc.repository.EduTripBusAssignmentRepository;
import com.elevata.gsrtc.repository.EducationalTripBookingRepository;
import com.elevata.gsrtc.repository.StaffRepository;
import com.elevata.gsrtc.service.EduTripBusService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EduTripBusServiceImpl implements EduTripBusService {
    private final EducationalTripBookingRepository bookingRepository;
    private final EduTripBusAssignmentRepository assignmentRepository;
    private final BusRepository busRepository;
    private final StaffRepository staffRepository;

    @Autowired
    public EduTripBusServiceImpl(EducationalTripBookingRepository bookingRepository, EduTripBusAssignmentRepository assignmentRepository, BusRepository busRepository, StaffRepository staffRepository) {
        this.bookingRepository = bookingRepository;
        this.assignmentRepository = assignmentRepository;
        this.busRepository = busRepository;
        this.staffRepository = staffRepository;
    }

    @Transactional
    @Override
    public String save(int bookingId, String busNumber, String driverName, String conductorName) {
        EducationalTripBooking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found."));
        Bus bus = busRepository.findByBusNumber(busNumber);
        Staff driver = staffRepository.findByFullName(driverName);
        Staff conductor = staffRepository.findByFullName(conductorName);

        EducationalTripBusAssignment tripBusAssignment = new EducationalTripBusAssignment();
        tripBusAssignment.setTripBooking(booking);
        tripBusAssignment.setBus(bus);
        tripBusAssignment.setDriver(driver);
        tripBusAssignment.setConductor(conductor);
        tripBusAssignment.setStatus(BusAssignmentStatus.ASSIGNED);

        assignmentRepository.save(tripBusAssignment);

        return "Bus and staff assigned successfully.";
    }
}
