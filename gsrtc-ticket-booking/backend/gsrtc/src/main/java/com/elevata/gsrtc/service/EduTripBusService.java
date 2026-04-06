package com.elevata.gsrtc.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public interface EduTripBusService {
    String save(int bookingId, String busNumber,
                String driverName, String conductorName);
}
