package com.elevata.gsrtc.service;

import com.elevata.gsrtc.dto.EduBookingResponseDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface EduBookingService {
    String save(Integer requestId);

    List<EduBookingResponseDTO> findAllBookings();
}
