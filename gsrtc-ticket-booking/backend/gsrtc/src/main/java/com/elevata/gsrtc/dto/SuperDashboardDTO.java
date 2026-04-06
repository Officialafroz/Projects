package com.elevata.gsrtc.dto;

import lombok.*;

@Getter @Setter
@Data
@Builder
public class SuperDashboardDTO {
    private long totalBuses;
    private long totalOnTripBuses;

    private long totalRoutes;
    private long totalTrips;

    private long registeredUsers;

    private long totalBookings;
    private double totalRevenue;

    private long totalSeatsBooked;

    private long totalDepots;
    private long totalStaff;

    private long totalEducationalTrips;
    private double educationalTripRevenue;

    private double averageMonthlyRevenue;
    private double averageMonthlyBookingPerUser;

    private double feedbackRating;

    public SuperDashboardDTO(long totalBuses, long totalOnTripBuses, long totalRoutes, long totalTrips, long registeredUsers, long totalBookings, double totalRevenue, long totalSeatsBooked, long totalDepots, long totalStaff, long totalEducationalTrips, double educationalTripRevenue, double averageMonthlyRevenue, double averageMonthlyBookingPerUser, double feedbackRating) {
        this.totalBuses = totalBuses;
        this.totalOnTripBuses = totalOnTripBuses;
        this.totalRoutes = totalRoutes;
        this.totalTrips = totalTrips;
        this.registeredUsers = registeredUsers;
        this.totalBookings = totalBookings;
        this.totalRevenue = totalRevenue;
        this.totalSeatsBooked = totalSeatsBooked;
        this.totalDepots = totalDepots;
        this.totalStaff = totalStaff;
        this.totalEducationalTrips = totalEducationalTrips;
        this.educationalTripRevenue = educationalTripRevenue;
        this.averageMonthlyRevenue = averageMonthlyRevenue;
        this.averageMonthlyBookingPerUser = averageMonthlyBookingPerUser;
        this.feedbackRating = feedbackRating;
    }
}
