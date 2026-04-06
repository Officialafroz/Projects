package com.elevata.gsrtc.service;

import com.elevata.gsrtc.dto.EduTripRequestCreateDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public interface EduTripRequestService {
    String createTripRequest(EduTripRequestCreateDTO dto);
}
