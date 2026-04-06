package com.elevata.gsrtc.dto;

import lombok.Data;

@Data
public class BusDepotSummaryDTO {

    private String depotName;
    private String address;
    private long totalBuses;
    private double revenue;
    private long totalStaff;
    private long totalBookings;

    public BusDepotSummaryDTO(String depotName, String address, long totalBuses, double revenue, long totalStaff, long totalBookings) {
        this.depotName = depotName;
        this.address = address;
        this.totalBuses = totalBuses;
        this.revenue = revenue;
        this.totalStaff = totalStaff;
        this.totalBookings = totalBookings;
    }

}
