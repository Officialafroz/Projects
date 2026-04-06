package com.elevata.gsrtc.service.impl;

import com.elevata.gsrtc.dto.RouteDTO;
import com.elevata.gsrtc.entity.BusDepot;
import com.elevata.gsrtc.repository.BusRouteRepository;
import com.elevata.gsrtc.entity.BusRoute;
import com.elevata.gsrtc.service.BusDepotService;
import com.elevata.gsrtc.service.RouteService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RouteServiceImpl implements RouteService {
    private final BusRouteRepository routeRepository;
    private final BusDepotService busDepotRepository;

    @Autowired
    public RouteServiceImpl(BusRouteRepository routeRepository, BusDepotService busDepotRepository) {
        this.routeRepository = routeRepository;
        this.busDepotRepository = busDepotRepository;
    }

    @Override
    public BusRoute findById(int routeId) {
        BusRoute optionalRoute = routeRepository.findById(routeId);

        if (optionalRoute != null) {
            return optionalRoute;
        } else {
            throw new RuntimeException("Booking not found for id: " + routeId);
        }
    }

    @Override
    @Transactional
    public String addRoute(RouteDTO routeDTO, String duration) {
        BusDepot busDepot = busDepotRepository.findById(routeDTO.getDepotId());
        int durationInMinutes = convertToMinutes(duration);

        BusRoute route = new BusRoute();
        route.setRouteName(routeDTO.getRouteName());
        route.setStartPoint(routeDTO.getStartingPoint());
        route.setEndPoint(routeDTO.getEndingPoint());
        route.setClassType(routeDTO.getClassType());
        route.setDistance(routeDTO.getDistance());
        route.setDuration(durationInMinutes);
        route.setBusDepot(busDepot);

        routeRepository.save(route);
        return "Route has been added to DB.";
    }

    @Override
    public List<RouteDTO> findAll() {
        List<BusRoute> routes = routeRepository.findAll();
        List<RouteDTO> routeDTOS = new ArrayList<>(routes.size());

        routes.forEach(route -> {
            RouteDTO routeDTO = new RouteDTO();
            routeDTO.setRouteName(route.getRouteName());
            routeDTO.setStartingPoint(route.getStartPoint());
            routeDTO.setEndingPoint(route.getEndPoint());
            routeDTO.setDepotId(route.getBusDepot().getDepotId());

            routeDTOS.add(routeDTO);
        });

        return routeDTOS;
    }

    @Override
    public List<RouteDTO> findByDepotId(int depotId) {
        List<BusRoute> routes = routeRepository.findAllByBusDepotDepotId(depotId);
        List<RouteDTO> routeDTOS = new ArrayList<>(routes.size());

        routes.forEach(route -> {
            RouteDTO routeDTO = new RouteDTO();
            routeDTO.setRouteId(route.getRouteId());
            routeDTO.setRouteName(route.getRouteName());
            routeDTO.setStartingPoint(route.getStartPoint());
            routeDTO.setEndingPoint(route.getEndPoint());
            routeDTO.setClassType(route.getClassType());
            routeDTO.setDistance(route.getDistance());
            routeDTO.setDuration(route.getDuration());
            routeDTO.setDepotId(route.getBusDepot().getDepotId());

            routeDTOS.add(routeDTO);
        });

        return routeDTOS;
    }

    private int convertToMinutes(String duration) {
        String[] parts = duration.split(":");
        int hours = Integer.parseInt(parts[0]);
        int minutes = Integer.parseInt(parts[1]);

        return hours * 60 + minutes;
    }

    @Transactional
    @Override
    public void save(BusRoute busRoute) {
        routeRepository.save(busRoute);
        System.out.println("Bus route is successfully created.");
    }

    @Transactional
    @Override
    public void delete(int routeId) {
        routeRepository.deleteById(routeId);
        System.out.println("Route is safely deleted.");
    }
}
