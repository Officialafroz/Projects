package com.elevata.gsrtc.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "registration_otp")
public class RegistrationOtp {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    int id;

    @Column(name = "otp")
    int otp;

    @Column(name = "email")
    String email;

    public RegistrationOtp(int otp, String email) {
        this.otp = otp;
        this.email = email;
    }
}
