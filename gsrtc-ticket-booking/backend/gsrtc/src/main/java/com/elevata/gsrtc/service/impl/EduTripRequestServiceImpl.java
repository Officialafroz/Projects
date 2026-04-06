package com.elevata.gsrtc.service.impl;

import com.elevata.gsrtc.dto.EduTripRequestCreateDTO;
import com.elevata.gsrtc.entity.BusDepot;
import com.elevata.gsrtc.entity.EducationalTripDestination;
import com.elevata.gsrtc.entity.EducationalTripRequest;
import com.elevata.gsrtc.entity.InstituteRegistry;
import com.elevata.gsrtc.enums.TripStatus;
import com.elevata.gsrtc.repository.*;
import com.elevata.gsrtc.service.EduTripRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EduTripRequestServiceImpl implements EduTripRequestService {
    private final EduTripRequestRepository tripRequestRepository;
    private final EduTripDestinationRepository destinationRepository;
    private final InstituteRepository instituteRegistry;
    private final BusDepotRepository busDepotRepository;
    private final AppUserRepository appUserRepository;

    @Autowired
    public EduTripRequestServiceImpl(EduTripRequestRepository tripRequestRepository, EduTripDestinationRepository destinationRepository, InstituteRepository instituteRegistry, BusDepotRepository busDepotRepository, AppUserRepository appUserRepository) {
        this.tripRequestRepository = tripRequestRepository;
        this.destinationRepository = destinationRepository;
        this.instituteRegistry = instituteRegistry;
        this.busDepotRepository = busDepotRepository;
        this.appUserRepository = appUserRepository;
    }

    @Transactional
    @Override
    public String createTripRequest(EduTripRequestCreateDTO dto) {

        InstituteRegistry institute = instituteRegistry.findById(dto.getInstituteId())
                .orElseThrow(() -> new RuntimeException("Institute not found"));

        BusDepot depot = busDepotRepository.findById(dto.getDepotId())
                .orElseThrow(() -> new RuntimeException("Depot not found."));

        EducationalTripRequest request = new EducationalTripRequest();
        request.setInstitute(institute);
        request.setBusDepot(depot);
        request.setTripName(dto.getTripName());
        request.setTripDays(dto.getTripDays());
        request.setRequestedBuses(dto.getRequestedBuses());
        request.setTotalPersons(dto.getTotalPersons());
        request.setBusClass(dto.getBusClass());
        request.setHotelRequired(dto.getHotelRequired());
        request.setTripStartDatetime(dto.getTripStartDatetime());
        request.setEmergencyContact(dto.getEmergencyContact());
        request.setAppUser(appUserRepository.findById(dto.getUserId()).orElseThrow());

        request.setStatus(TripStatus.PENDING);
        request.setBudget(dto.getBudget());

        tripRequestRepository.save(request);

        destinationRepository.save(new EducationalTripDestination(request,
                dto.getSourceLocation(),
                "SOURCE",
                1));

        int sequenceNo = saveLocation(request, dto.getDestinationLocations(), "DESTINATION", 2);

        if (request.getHotelRequired()) {
            saveLocation(request, dto.getHotels(), "HOTEL", sequenceNo);
        }

        return "Trip request created successfully";
    }

    private int saveLocation(EducationalTripRequest request,
                              List<String> locations,
                              String locationType,
                             int sequenceNo) {
        for (String location : locations) {
            destinationRepository.save(
                    new EducationalTripDestination(
                    request,
                    location,
                    locationType,
                    sequenceNo++
            ));
        }

        return sequenceNo;
    }
}
