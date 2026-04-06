package com.elevata.gsrtc.service.impl;

import com.elevata.gsrtc.dto.RouteStopDTO;
import com.elevata.gsrtc.repository.BusRouteRepository;
import com.elevata.gsrtc.repository.RouteStopsRepository;
import com.elevata.gsrtc.entity.BusRoute;
import com.elevata.gsrtc.entity.RouteStops;
import com.elevata.gsrtc.service.RouteService;
import com.elevata.gsrtc.service.RouteStopsService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RouteStopsServiceImpl implements RouteStopsService {
    private final RouteStopsRepository stopsRepository;
    private final RouteService routeService;
    private final BusRouteRepository routeRepository;
    private final RouteStopsRepository routeStopsRepository;

    @Autowired
    public RouteStopsServiceImpl(RouteStopsRepository stopsRepository, RouteService routeService, BusRouteRepository routeRepository, RouteStopsRepository routeStopsRepository) {
        this.stopsRepository = stopsRepository;
        this.routeService = routeService;
        this.routeRepository = routeRepository;
        this.routeStopsRepository = routeStopsRepository;
    }

    @Override
    public List<RouteStops> findAll() {
        return stopsRepository.findAll();
    }

    @Override
    public RouteStops findById(int stopId) {
        Optional<RouteStops> stopsOptional = stopsRepository.findById(stopId);

        if (stopsOptional.isEmpty()) {
            throw new RuntimeException("Route stop not found for id: " + stopId);
        }

        return stopsOptional.get();
    }

    @Override
    public List<RouteStopDTO> findRouteStops(int routeId) {
        List<RouteStops> stops = routeStopsRepository.findByBusRouteRouteId(routeId);
        List<RouteStopDTO> stopDTOS = new ArrayList<>(stops.size());

        stops.forEach(stop -> {
            RouteStopDTO stopDTO = new RouteStopDTO();
            stopDTO.setStopName(stop.getStopName());
            stopDTO.setStopOrder(stop.getStopOrder());
            stopDTO.setDistanceFromStart(stop.getDistanceFromStart());
            stopDTO.setDuration(stop.getDuration());
            stopDTO.setFare(stop.getFare());

            stopDTOS.add(stopDTO);
        });
//        System.out.println(stopDTOS);

        return stopDTOS;
    }

    @Override
    @Transactional
    public String save(RouteStopDTO stopDTO) {
        BusRoute route = routeRepository.findById(stopDTO.getRouteId());

        RouteStops stop = new RouteStops();
        System.out.println("Route dur: " + route.getDuration());
        System.out.println("Route dis: " + route.getDistance());
        System.out.println("Stop dur: " + stop.getDistanceFromStart());
        int duration = generateDuration(route.getDuration(),
                route.getDistance(), stopDTO.getDistanceFromStart());
//        System.out.println(duration);

        stop.setStopName(stopDTO.getStopName());
        stop.setStopOrder(stopDTO.getStopOrder());
        stop.setDistanceFromStart(stopDTO.getDistanceFromStart());
        stop.setDuration(duration);
        stop.setFare(stopDTO.getFare());
        stop.setBusRoute(route);

        routeStopsRepository.save(stop);
        return "Stop saved.";
    }

    @Override
    @Transactional
    public void delete(String stopName) {
        RouteStops stop = routeStopsRepository.findByStopName(stopName);
        routeStopsRepository.delete(stop);
    }


    public int generateDuration(int routeDuration, double routeDistance, double stopDistance) {
        return (int) ((routeDuration/routeDistance) * stopDistance);
    }

    @Transactional
    @Override
    public void save(RouteStops stops) {
        if (stops.getBusRoute() != null && stops.getBusRoute().getRouteId() > 0) {
            int routeId = stops.getBusRoute().getRouteId();
            BusRoute existingRoute = routeService.findById(routeId);

            if (existingRoute == null) {
                throw new RuntimeException(
                        String.format("Invalid route id - %d", routeId));
            }
            stops.setBusRoute(existingRoute);
        }

        stopsRepository.save(stops);
        System.out.println("Route stops is successfully created.");
    }

    @Transactional
    @Override
    public void delete(int stopId) {
        stopsRepository.deleteById(stopId);
        System.out.println("Route stop is safely deleted.");
    }
}
