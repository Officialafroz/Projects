package com.elevata.gsrtc.service;

import com.elevata.gsrtc.dto.TripRequestDetailDTO;
import com.elevata.gsrtc.dto.TripRequestOverviewDTO;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public interface TripRequestManagementService {
    List<TripRequestOverviewDTO> findAllNewestFirst(int depotId);

    TripRequestDetailDTO getTripDetails(Integer tripRequestId);

    String updateBudget(Integer tripRequestId, BigDecimal budget);

    String updateStatus(Integer requestId, String status);

    List<TripRequestDetailDTO> getTripRequestList(Integer userId);
}
