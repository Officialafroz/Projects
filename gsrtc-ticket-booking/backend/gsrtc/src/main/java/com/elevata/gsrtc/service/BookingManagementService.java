package com.elevata.gsrtc.service;

import com.elevata.gsrtc.dto.BookingManagementTripDTO;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public interface BookingManagementService {
    BookingManagementTripDTO filterByTripCode(
            int depotId, String tripCode
    );

    //Add Error handling
    List<BookingManagementTripDTO> filterByRouteAndClassAndRevenue(
            int depotId, String route, String classType, BigDecimal revenue
    );

    List<BookingManagementTripDTO> findAll(int depotId);

    List<BookingManagementTripDTO> findAllByDate(int depotId, LocalDate date);
}
