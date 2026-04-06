package com.elevata.gsrtc.service;

import com.elevata.gsrtc.dto.PassengerManagementDTO;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public interface PassengerManagementService {
    List<PassengerManagementDTO> findAll(Integer depotId);

    List<PassengerManagementDTO> filterByPnrOrPassengerReference(
            String pnr, String passRef
    );

    List<PassengerManagementDTO> filterByBusNumberOrTripCodeOrTotalSeats(
            String busNumber, String tripCode, Integer totalSeats
    );

    List<PassengerManagementDTO> filterByRouteAndBusTypeAndRevenue(
            String route, String classType, BigDecimal revenue
    );
}
