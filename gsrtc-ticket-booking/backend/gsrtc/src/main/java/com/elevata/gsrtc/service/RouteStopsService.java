package com.elevata.gsrtc.service;

import com.elevata.gsrtc.dto.RouteStopDTO;
import com.elevata.gsrtc.entity.RouteStops;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public interface RouteStopsService {
    List<RouteStops> findAll();

    RouteStops findById(int stopId);

    List<RouteStopDTO> findRouteStops(int routeId);

    String save(RouteStopDTO stopDTO);

    void delete(String stopName);

    void save(RouteStops stops);

    void delete(int stopId);
}
