package com.elevata.gsrtc.repository;

import com.elevata.gsrtc.entity.RouteStops;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RouteStopsRepository extends JpaRepository<RouteStops, Integer> {
    List<RouteStops> findByBusRouteRouteId(int routeId);
    RouteStops findByStopName(String stopName);
    List<RouteStops> findAllByStopName(String stopName);

}
