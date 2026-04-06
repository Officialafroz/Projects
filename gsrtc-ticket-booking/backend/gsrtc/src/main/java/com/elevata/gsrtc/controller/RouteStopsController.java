package com.elevata.gsrtc.controller;

import com.elevata.gsrtc.dto.RouteStopDTO;
import com.elevata.gsrtc.entity.RouteStops;
import com.elevata.gsrtc.service.RouteStopsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/depot/route-stops")
public class RouteStopsController {
    private RouteStopsService routeStopsService;

    @Autowired
    public RouteStopsController(RouteStopsService routeStopsService) {
        this.routeStopsService = routeStopsService;
    }

    @GetMapping("/routeStops")
    public List<RouteStops> getRouteList() {
        return routeStopsService.findAll();
    }

    @GetMapping("/{stopId}")
    public RouteStops getStopById(@PathVariable int stopId) {
        return routeStopsService.findById(stopId);
    }

    @GetMapping("/{routeId}")
    public List<RouteStopDTO> findRouteStops(@PathVariable int routeId) {
        return routeStopsService.findRouteStops(routeId);
    }

    @PostMapping
    public String add(@RequestBody RouteStopDTO stopDTO) {
        return routeStopsService.save(stopDTO);
    }

    @DeleteMapping("/{stopName}")
    public ResponseEntity<String> delete(@PathVariable String stopName) {
        routeStopsService.delete(stopName);

        return ResponseEntity.ok("Stop deleted.");
    }
    
    @PostMapping("/add")
    public void add(@RequestBody RouteStops stops) {
        routeStopsService.save(stops);
    }

    @DeleteMapping("/delete/{stopId}")
    public void delete(@PathVariable int stopId) {
        routeStopsService.delete(stopId);
    }
}
