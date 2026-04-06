package com.elevata.gsrtc.service.impl;

import com.elevata.gsrtc.dto.PassengerManagementDTO;
import com.elevata.gsrtc.repository.BookingRepository;
import com.elevata.gsrtc.repository.PassengerRepository;
import com.elevata.gsrtc.service.PassengerManagementService;
import com.elevata.gsrtc.util.StringNormalizer;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class PassengerManagementServiceImpl implements PassengerManagementService {
    private final PassengerRepository passengerRepository;
    private final BookingRepository bookingRepository;

    @Autowired
    public PassengerManagementServiceImpl(PassengerRepository passengerRepository, BookingRepository bookingRepository) {
        this.passengerRepository = passengerRepository;
        this.bookingRepository = bookingRepository;
    }

    @Override
    public List<PassengerManagementDTO> findAll(Integer depotId) {
        return passengerRepository.findAllByDepotId(depotId);
    }

    @Override
    public List<PassengerManagementDTO> filterByPnrOrPassengerReference(
            String pnr, String passRef
    ) {
        return passengerRepository.findAllByPnrOrPassengerReference(pnr, passRef);
    }

    @Override
    public List<PassengerManagementDTO> filterByBusNumberOrTripCodeOrTotalSeats(
            String busNumber, String tripCode, Integer totalSeats
    ) {
        busNumber = StringNormalizer.normalize(busNumber);
        tripCode = StringNormalizer.normalize(tripCode);

        return passengerRepository.findAllByBusNumberOrTripCodeOrTotalSeats(busNumber, tripCode, totalSeats);
    }

    @Override
    public List<PassengerManagementDTO> filterByRouteAndBusTypeAndRevenue(
            String route, String classType, BigDecimal revenue
    ) {
        route = StringNormalizer.normalize(route);
        classType = StringNormalizer.normalize(classType);

        return passengerRepository.findPassengers(route, classType, revenue);
    }
}
