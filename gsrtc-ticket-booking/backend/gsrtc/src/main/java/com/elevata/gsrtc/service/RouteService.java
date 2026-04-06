package com.elevata.gsrtc.service;

import com.elevata.gsrtc.dto.RouteDTO;
import com.elevata.gsrtc.entity.BusRoute;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RouteService {
    List<RouteDTO> findAll();

    List<RouteDTO> findByDepotId(int depotId);

    BusRoute findById(int routeId);

    String addRoute(RouteDTO routeDTO, String duration);

    void save(BusRoute busRoute);

    void delete(int routeId);
}
