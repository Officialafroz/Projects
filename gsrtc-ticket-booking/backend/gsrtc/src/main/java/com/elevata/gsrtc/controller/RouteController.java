package com.elevata.gsrtc.controller;

import com.elevata.gsrtc.dto.RouteDTO;
import com.elevata.gsrtc.entity.BusRoute;
import com.elevata.gsrtc.service.RouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/depot/route")
public class RouteController {
    private RouteService routeService;

    @Autowired
    public RouteController(RouteService routeService) {
        this.routeService = routeService;
    }

    @PostMapping
    public ResponseEntity<String> addRoute(@RequestBody RouteDTO routeDTO,
                                           @RequestParam String duration) {
        routeService.addRoute(routeDTO, duration);
        return ResponseEntity.ok("Route has been added to DB.");
    }

    @GetMapping("/list")
    public List<RouteDTO> findAll() {
        return routeService.findAll();
    }

    @GetMapping("/list/{depotId}")
    public List<RouteDTO> findByDepotId(@PathVariable int depotId) {
        return routeService.findByDepotId(depotId);
    }
    
    @GetMapping("/{routeId}")
    public BusRoute getRouteById(@PathVariable int routeId) {
        return routeService.findById(routeId);
    }

    @PostMapping("/add")
    public void add(@RequestBody BusRoute route) {
        routeService.save(route);
    }

    @DeleteMapping("/delete/{routeId}")
    public void delete(@PathVariable int routeId) {
        routeService.delete(routeId);
    }
}
