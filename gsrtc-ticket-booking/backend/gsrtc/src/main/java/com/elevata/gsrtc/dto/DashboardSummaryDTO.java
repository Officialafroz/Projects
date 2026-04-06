package com.elevata.gsrtc.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class DashboardSummaryDTO {
    private BigDecimal totalRevenue;
    private Double revenueGrowthPercent;

    private Integer activeBuses;
    private Integer activeBusesChange;

    private Long totalBookings;
    private Double bookingGrowthPercent;

    private Integer registeredUsers;
    private Integer newUsersThisWeek;
}
