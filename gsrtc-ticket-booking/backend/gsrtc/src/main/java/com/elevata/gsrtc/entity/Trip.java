package com.elevata.gsrtc.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "trip")
public class Trip {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "trip_id")
    private int tripId;

    @Column(name = "trip_code")
    private String tripCode;

    @Column(name = "date")
    private LocalDate tripDate;

    @Column(name = "departure_time")
    private LocalDateTime departureTime;

    @Column(name = "destination_time")
    private LocalDateTime destinationTime;

    @Column(name = "status")
    private String status;

    @OneToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH},
              fetch = FetchType.EAGER)
    @JoinColumn(name = "bus_id")
    private Bus bus;

    @OneToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH},
              fetch = FetchType.EAGER)
    @JoinColumn(name = "route_id")
    private BusRoute route;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH},
            fetch = FetchType.LAZY)
    @JoinColumn(name = "depot_id")
    private BusDepot busDepot;
}
