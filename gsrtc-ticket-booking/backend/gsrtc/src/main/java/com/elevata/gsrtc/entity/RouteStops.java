package com.elevata.gsrtc.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@Entity
@Table(name = "route_stop")
public class RouteStops {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "stop_id")
    private int stopId;

    @Column(name = "stop_name")
    private String stopName;

    @Column(name = "stop_order")
    private int stopOrder;

    @Column(name = "distance_from_start")
    private double distanceFromStart;

    @Column(name = "duration")
    private int duration;

    @Column(name = "fare")
    private double fare;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.REFRESH, CascadeType.DETACH}, fetch = FetchType.LAZY)
    @JoinColumn(name = "route_id")
    @JsonIgnore
    private BusRoute busRoute;

    public RouteStops(String stopName, int stopOrder, double distanceFromStart, double fare) {
        this.stopName = stopName;
        this.stopOrder = stopOrder;
        this.distanceFromStart = distanceFromStart;
        this.fare = fare;
    }
}
