package com.elevata.gsrtc.service.impl;

import com.elevata.gsrtc.dto.EduBookingResponseDTO;
import com.elevata.gsrtc.dto.EduTripRequestCreateDTO;
import com.elevata.gsrtc.entity.EducationalTripBooking;
import com.elevata.gsrtc.entity.EducationalTripRequest;
import com.elevata.gsrtc.enums.BookingStatus;
import com.elevata.gsrtc.enums.TripStatus;
import com.elevata.gsrtc.repository.EduPaymentRepository;
import com.elevata.gsrtc.repository.EduTripDestinationRepository;
import com.elevata.gsrtc.repository.EduTripRequestRepository;
import com.elevata.gsrtc.repository.EducationalTripBookingRepository;
import com.elevata.gsrtc.service.EduBookingService;
import com.elevata.gsrtc.service.ReferenceNumberService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EduBookingServiceImpl implements EduBookingService {
    private final EducationalTripBookingRepository bookingRepository;
    private final EduTripRequestRepository requestRepository;
    private final EduTripDestinationRepository destinationRepository;
    private final EduPaymentRepository paymentRepository;

    @Autowired
    public EduBookingServiceImpl(EducationalTripBookingRepository bookingRepository, EduTripRequestRepository requestRepository, EduTripDestinationRepository destinationRepository, EduPaymentRepository paymentRepository) {
        this.bookingRepository = bookingRepository;
        this.requestRepository = requestRepository;
        this.destinationRepository = destinationRepository;
        this.paymentRepository = paymentRepository;
    }

    @Transactional
    @Override
    public String save(Integer requestId) {
        String pnr = ReferenceNumberService.generatePNR();

        EducationalTripRequest request = requestRepository.findById(requestId).orElseThrow();
        EduTripRequestCreateDTO requestCreateDTO = fetchTripRequestData(request);

        EducationalTripBooking booking = getEducationalTripBooking(request, pnr, requestCreateDTO);

        bookingRepository.save(booking);

        return "Educational booking created successfully.";
    }

    @Override
    public List<EduBookingResponseDTO> findAllBookings() {
        return bookingRepository
                .findAll()
                .stream()
                .map(booking -> {
                    EduTripRequestCreateDTO requestCreateDTO = fetchTripRequestData(booking.getTripRequest());
                    return new EduBookingResponseDTO(
                            requestCreateDTO,
                            booking.getPnr(),
                            booking.getAllocatedBuses(),
                            booking.getTotalFare(),
                            booking.getBookingStatus().name()
                    );
                }).toList();
    }

    private static EducationalTripBooking getEducationalTripBooking(
            EducationalTripRequest request, String pnr,
            EduTripRequestCreateDTO requestCreateDTO
    ) {
        //        booking.setTripRequest(request);
//        booking.setPnr(pnr);
//        booking.setAllocatedBuses(requestCreateDTO.getRequestedBuses());
//        booking.setTotalFare(request.getBudget());

//        System.out.println("-".repeat(30) + request.getStatus());

        return new EducationalTripBooking(
                request,
                pnr,
                request.getRequestedBuses(),
                request.getBudget(),
                getBookingStatus(request.getStatus())
        );
    }

    private EduTripRequestCreateDTO fetchTripRequestData(EducationalTripRequest request) {
        return new EduTripRequestCreateDTO(
                request.getInstitute().getInstituteId(),
                request.getBusDepot().getDepotId(),
                request.getAppUser().getUserId(),
                request.getTripName(),
                destinationRepository.findDestinationName(request.getRequestId(), "SOURCE"),
                destinationRepository.findLocationsByIdAndType(request.getRequestId(), "DESTINATION"),
                destinationRepository.findLocationsByIdAndType(request.getRequestId(), "HOTEL"),
                request.getTripDays(),
                request.getRequestedBuses(),
                request.getTotalPersons(),
                request.getBusClass(),
                request.getBudget(),
                request.getHotelRequired(),
                request.getTripStartDatetime(),
                request.getEmergencyContact());
    }

    private static BookingStatus getBookingStatus(TripStatus status) {
//        String statusName = request.getStatus().name();

//        if (!statusName.equals("APPROVED")) {
//            if (statusName.equals("UNDER_PROCESS")) {
//                System.out.println("-".repeat(40) + "Inside under process block");
//                booking.setBookingStatus(BookingStatus.UNDER_PROCESS);
//                System.out.println("-".repeat(40) + "After under process set statement.");
//
//            } else if (statusName.equals("COMPLETED")) {
//                booking.setBookingStatus(BookingStatus.COMPLETED);
//            } else {
//                booking.setBookingStatus(BookingStatus.CANCELLED);
//            }
//        } else {
//            booking.setBookingStatus(BookingStatus.CONFIRMED);
//        }

        return switch (status.name()) {
            case "APPROVED" -> BookingStatus.CONFIRMED;
            case "UNDER_PROCESS" -> BookingStatus.UNDER_PROCESS;
            case "COMPLETED" -> BookingStatus.COMPLETED;
            default -> BookingStatus.CANCELLED;
        };
    }
}
