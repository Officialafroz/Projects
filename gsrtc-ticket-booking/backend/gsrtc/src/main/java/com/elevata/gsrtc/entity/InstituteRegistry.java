package com.elevata.gsrtc.entity;

import com.elevata.gsrtc.enums.Ownership;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Entity
@Table(name = "institute_registry")
public class InstituteRegistry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "institute_id")
    private Integer instituteId;

    @Column(name = "institute_code", length = 11, nullable = false, unique = true)
    private String instituteCode;

    @Column(name = "institute_name", length = 200, nullable = false)
    private String instituteName;

    @Column(name = "institute_type", length = 25, nullable = false)
    private String instituteType; // School / College

    @Enumerated(EnumType.STRING)
    @Column(name = "ownership", nullable = false)
    private Ownership ownership;

    @Column(name = "location", length = 255, nullable = false)
    private String location;

    @Column(name = "institute_phone", length = 10)
    private String institutePhone;

    @Column(name = "institute_email", length = 100)
    private String instituteEmail;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }
}
