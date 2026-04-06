package com.elevata.gsrtc.entity;

import com.elevata.gsrtc.enums.TripStatus;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@NoArgsConstructor
@Data
@Entity
@Table(name = "edu_trip_request")
public class EducationalTripRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "request_id")
    private Integer requestId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "institute_id", nullable = false)
    private InstituteRegistry institute;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "depot_id", nullable = false)
    private BusDepot busDepot;

    @Column(name = "trip_name", length = 20, nullable = false)
    private String tripName;

    @Column(name = "trip_days", nullable = false)
    private Integer tripDays;

    @Column(name = "requested_buses", nullable = false)
    private Integer requestedBuses;

    @Column(name = "total_persons", nullable = false)
    private Integer totalPersons;

    @Column(name = "bus_class", length = 20, nullable = false)
    private String busClass;

    @Column(name = "budget", precision = 8, scale = 2)
    private BigDecimal budget;

    @Column(name = "hotel_required")
    private Boolean hotelRequired;

    @Column(name = "trip_start_datetime", nullable = false)
    private LocalDateTime tripStartDatetime;

    @Column(name = "emergency_contact", length = 10, nullable = false)
    private String emergencyContact;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private TripStatus status = TripStatus.PENDING;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private AppUser appUser;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
