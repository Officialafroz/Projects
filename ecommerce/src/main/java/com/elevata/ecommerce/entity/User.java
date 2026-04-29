package com.elevata.ecommerce.entity;

import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private int userId;

    @Column(name = "name")
    private String name;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "pincode")
    private int pincode;

    @Column(name = "address")
    private String address;

    public User(String name, String phoneNumber, int pincode, String address) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.pincode = pincode;
        this.address = address;
    }
}
