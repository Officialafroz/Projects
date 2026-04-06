package com.elevata.gsrtc.service;

import com.elevata.gsrtc.dto.GraphDTO;
import com.elevata.gsrtc.dto.SuperDashboardDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SuperDashboardService {
    SuperDashboardDTO getDashboard(int month, int year);

    List<GraphDTO> getBookingTrends(int year);

    List<GraphDTO> getRevenueTrends(int year);

    List<GraphDTO> getUsersGrowth(int year);

    List<GraphDTO> getSeatOccupancy(int year);
//
//    List<GraphDataDTO> getRoutePopularity();
}
