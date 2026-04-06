package com.elevata.gsrtc.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@Entity
@Table(name = "bus_depot")
public class BusDepot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "depot_id")
    private int depotId;

    @Column(name = "depot_name")
    private String depotName;

    @Column(name = "city")
    private String city;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "address")
    private String address;

    public BusDepot(String depotName, String city, String address) {
        this.depotName = depotName;
        this.city = city;
        this.address = address;
    }

    public BusDepot(String depotName, String city, String email, String password, String address) {
        this.depotName = depotName;
        this.city = city;
        this.email = email;
        this.password = password;
        this.address = address;
    }
}
