package com.elevata.gsrtc.service.impl;

import com.elevata.gsrtc.dto.TripRequestDetailDTO;
import com.elevata.gsrtc.dto.TripRequestOverviewDTO;
import com.elevata.gsrtc.entity.EducationalTripRequest;
import com.elevata.gsrtc.enums.TripStatus;
import com.elevata.gsrtc.exception.TripNotFoundException;
import com.elevata.gsrtc.repository.EduPaymentRepository;
import com.elevata.gsrtc.repository.EduTripDestinationRepository;
import com.elevata.gsrtc.repository.EduTripRequestRepository;
import com.elevata.gsrtc.service.TripRequestManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class TripRequestManagementServiceImpl implements TripRequestManagementService {
    private final EduTripRequestRepository educationalTripRepository;
    private final EduTripDestinationRepository destinationRepository;
    private final EduPaymentRepository paymentRepository;

    @Autowired
    public TripRequestManagementServiceImpl(EduTripRequestRepository educationalTripRepository, EduTripDestinationRepository destinationRepository, EduPaymentRepository paymentRepository) {
        this.educationalTripRepository = educationalTripRepository;
        this.destinationRepository = destinationRepository;
        this.paymentRepository = paymentRepository;
    }

    @Override
    public List<TripRequestOverviewDTO> findAllNewestFirst(int depotId) {
        return educationalTripRepository.findAllNewestFirst(depotId, "SOURCE");
    }

    @Override
    public TripRequestDetailDTO getTripDetails(Integer tripRequestId) {
        EducationalTripRequest trip = educationalTripRepository.findById(tripRequestId)
                .orElseThrow(() ->
                        new RuntimeException("Trip request not found"));

        return makeDto(tripRequestId, trip);
    }

    @Transactional
    @Override
    public String updateBudget(
            Integer tripRequestId, BigDecimal budget) {

        EducationalTripRequest trip = educationalTripRepository.findById(tripRequestId)
                .orElseThrow(() -> new RuntimeException("Trip request not found"));

        trip.setBudget(budget);
        trip.setStatus(TripStatus.APPROVED);

        educationalTripRepository.save(trip);

        return "Budget updated.";
    }

    @Transactional
    @Override
    public String updateStatus(Integer requestId, String status) {
        EducationalTripRequest request = educationalTripRepository.findById(requestId)
                .orElseThrow(() -> new TripNotFoundException("Trip request not found."));

        request.setStatus(switch (status) {
            case "UNDER_PROCESS" -> TripStatus.APPROVED;
            default -> TripStatus.REJECTED;
        });

        educationalTripRepository.save(request);
        return "Status updated.";
    }

    @Override
    public List<TripRequestDetailDTO> getTripRequestList(Integer userId) {
        List<EducationalTripRequest> trips = educationalTripRepository
                .findByAppUserUserId(userId)
                .orElseThrow(() -> new RuntimeException("Trips not found"));

        return makeDtoList(trips);

    }

    private List<TripRequestDetailDTO> makeDtoList(List<EducationalTripRequest> trips) {
        List<TripRequestDetailDTO> dtoList = null;

        if (!trips.isEmpty()) {
            dtoList = new ArrayList<>(trips.size());

            for (EducationalTripRequest tripRequest : trips) {
                dtoList.add(makeDto(tripRequest.getRequestId(), tripRequest));
            }
        }

        return dtoList;
    }

    private TripRequestDetailDTO makeDto(int tripRequestId, EducationalTripRequest trip) {
        TripRequestDetailDTO dto = new TripRequestDetailDTO();
        dto.setRequestId(trip.getRequestId());
        dto.setInstituteName(trip.getInstitute().getInstituteName());
        dto.setInstituteCode(trip.getInstitute().getInstituteCode());
        dto.setInstituteType(trip.getInstitute().getInstituteType());
        dto.setTripName(trip.getTripName());
        dto.setSourceLocation(
                destinationRepository.findDestinationName(
                        tripRequestId, "SOURCE"));
        dto.setDestinationLocations(
                destinationRepository.findLocationsByIdAndType(
                        tripRequestId, "DESTINATION"));
        dto.setHotels(
                destinationRepository.findLocationsByIdAndType(
                        tripRequestId, "HOTEL"));
        dto.setTripDays(trip.getTripDays());
        dto.setRequestedBuses(trip.getRequestedBuses());
        dto.setTotalPersons(trip.getTotalPersons());
        dto.setBusClass(trip.getBusClass());
        dto.setBudget(trip.getBudget());
        dto.setHotelRequired(trip.getHotelRequired());
        dto.setTripStartDatetime(trip.getTripStartDatetime());
        dto.setEmergencyContact(trip.getEmergencyContact());
        dto.setTripStatus(trip.getStatus());

        return dto;
    }

//    public String receiveAdvancePayment(
//            Integer tripRequestId,
//            BigDecimal paidAmount,
//            PaymentMethod paymentMethod
//    ) {
//
//        EducationalTripRequest trip = educationalTripRepository.findById(tripRequestId)
//                .orElseThrow(() -> new RuntimeException("Trip request not found"));
//
//        if (trip.getBudget() == null) {
//            throw new RuntimeException("Budget not finalized for this trip");
//        }
//
//        LocalDateTime baseTime = trip.getUpdatedAt() != null
//                ? trip.getUpdatedAt()
//                : trip.getCreatedAt();
//
//        if (baseTime.plusHours(72).isBefore(LocalDateTime.now())) {
//            throw new RuntimeException("Advance payment window (72 hours) has expired");
//        }
//
//        BigDecimal requiredAdvance = trip.getBudget()
//                .multiply(BigDecimal.valueOf(0.5))
//                .setScale(2, RoundingMode.HALF_UP);
//
//        if (paidAmount.compareTo(requiredAdvance) < 0) {
//            throw new RuntimeException("Insufficient advance payment");
//        }
//
//        EduPayment payment = new EduPayment();
//        payment.setEducationalTripBooking(trip);
//        payment.setAmount(requiredAdvance);
//        payment.setMethod(paymentMethod);
//        payment.setStatus(PaymentStatus.PAID);
//        payment.setTransactionRef(ReferenceNumberService.generateTransRef());
//
//        paymentRepository.save(payment);
//
//        trip.setStatus(TripStatus.APPROVED);
//        educationalTripRepository.save(trip);
//
//        return "Advance payment received successfully";
//    }
}
