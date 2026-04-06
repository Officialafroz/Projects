package com.elevata.gsrtc.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Data
@Entity
@Table(name = "bus_route")
public class BusRoute {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "route_id")
    private int routeId;

    @Column(name = "route_name")
    private String routeName;

    @Column(name = "start_point")
    private String startPoint;

    @Column(name = "end_point")
    private String endPoint;

    @Column(name = "class_type")
    private String classType;

    @Column(name = "distance")
    private double distance;

    @Column(name = "duration")
    private int duration;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.REFRESH, CascadeType.DETACH})
    @JoinColumn(name = "depot_id")
    private BusDepot busDepot;

//    @OneToMany(mappedBy = "busRoute", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    private List<RouteStops> routeStopsList;

    public BusRoute(String routeName, String startPoint, String endPoint) {
        this.routeName = routeName;
        this.startPoint = startPoint;
        this.endPoint = endPoint;
    }
}
