package com.elevata.gsrtc.service.impl;

import com.elevata.gsrtc.dto.GraphDTO;
import com.elevata.gsrtc.dto.SuperDashboardDTO;
import com.elevata.gsrtc.repository.*;
import com.elevata.gsrtc.service.SuperDashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SuperDashboardServiceImpl implements SuperDashboardService {
    private final AppUserRepository userRepository;
    private final BusRepository busRepository;
    private final BusRouteRepository routeRepository;
    private final TripRepository tripRepository;
    private final BookingRepository bookingRepository;
    private final PassengerRepository passengerRepository;
    private final BusDepotRepository depotRepository;
    private final StaffRepository staffRepository;
    private final EducationalTripBookingRepository educationalTripRepository;
//    private final FeedbackRepository feedbackRepository;

    @Autowired
    public SuperDashboardServiceImpl(AppUserRepository userRepository, BusRepository busRepository, BusRouteRepository routeRepository, TripRepository tripRepository, BookingRepository bookingRepository, PassengerRepository passengerRepository, BusDepotRepository depotRepository, StaffRepository staffRepository, EducationalTripBookingRepository educationalTripRepository) {
        this.userRepository = userRepository;
        this.busRepository = busRepository;
        this.routeRepository = routeRepository;
        this.tripRepository = tripRepository;
        this.bookingRepository = bookingRepository;
        this.passengerRepository = passengerRepository;
        this.depotRepository = depotRepository;
        this.staffRepository = staffRepository;
        this.educationalTripRepository = educationalTripRepository;
    }

    @Override
    public SuperDashboardDTO getDashboard(int month, int year) {

        return SuperDashboardDTO.builder()
                .totalBuses(busRepository.count())
                .totalOnTripBuses(busRepository.countOnTripBuses(month, year))
                .totalRoutes(routeRepository.countRoutesByMonth())
                .totalTrips(tripRepository.countTripsByMonth(month, year))
                .registeredUsers(userRepository.countUsersByMonth(month, year))
                .totalBookings(bookingRepository.countBookingsByMonth(month, year))
                .totalRevenue(bookingRepository.getRevenueByMonth(month, year))
                .totalSeatsBooked(passengerRepository.totalSeatsBookedByMonth(month, year))
                .totalDepots(depotRepository.count())
                .totalStaff(staffRepository.count())
                .totalEducationalTrips(educationalTripRepository.count())
                .educationalTripRevenue(educationalTripRepository.totalEducationalTripRevenue())
                .feedbackRating(0)
                .build();

    }

    @Override
    public List<GraphDTO> getBookingTrends(int year) {

        return bookingRepository.getMonthlyBookingTrends(year);
    }
//
    @Override
    public List<GraphDTO> getRevenueTrends(int year) {
        return bookingRepository.getMonthlyRevenueTrends(year);
    }

    @Override
    public List<GraphDTO> getUsersGrowth(int year) {
        return userRepository.getUserGrowth(year);
    }

    @Override
    public List<GraphDTO> getSeatOccupancy(int year) {
        return passengerRepository.getSeatOccupancy(year);
    }

//    @Override
//    public List<GraphDataDTO> getRoutePopularity() {
//        return routeRepository.getRoutePopularity();
//    }
}
