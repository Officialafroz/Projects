package com.elevata.gsrtc.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@Entity
@Table(name = "passenger")
public class Passenger {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "passenger_id")
    private int passengerId;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH, CascadeType.REMOVE})
    @JoinColumn(name = "booking_id")
    private Booking booking;

    @Column(name = "name")
    private String name;

    @Column(name = "age")
    private int age;

    @Column(name = "gender")
    private String gender;

    @Column(name = "seat_no")
    private int seatNo;

    @Column(name = "pass_ref")
    private String passRef;

    @Column(name = "boarding_point")
    private String boardingPoint;

    @Column(name = "destination_point")
    private String destinationPoint;

    @Column(name = "individual_fare")
    private double individualFare;

    @Column(name = "status")
    private String status;

    public Passenger(Booking booking, String name, int age, String gender, int seatNo,
                     String boardingPoint, String destinationPoint, double individualFare,
                     String status) {
        this.booking = booking;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.seatNo = seatNo;
        this.boardingPoint = boardingPoint;
        this.destinationPoint = destinationPoint;
        this.individualFare = individualFare;
        this.status = status;
    }
}
