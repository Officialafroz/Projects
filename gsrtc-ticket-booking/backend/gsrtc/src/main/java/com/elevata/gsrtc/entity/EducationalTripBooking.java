package com.elevata.gsrtc.entity;

import com.elevata.gsrtc.enums.BookingStatus;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@NoArgsConstructor
@Data
@Entity
@Table(name = "edu_trip_booking")
public class EducationalTripBooking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "booking_id")
    private Integer bookingId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "request_id", nullable = false)
    private EducationalTripRequest tripRequest;

    @Column(name = "pnr", length = 12, nullable = false, unique = true)
    private String pnr;

    @Column(name = "allocated_buses", nullable = false)
    private Integer allocatedBuses;

    @Column(name = "total_fare", precision = 10, scale = 2, nullable = false)
    private BigDecimal totalFare;

    @Enumerated(EnumType.STRING)
    @Column(name = "booking_status")
    private BookingStatus bookingStatus;

    @Column(name = "booked_at", updatable = false)
    private LocalDateTime bookedAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public EducationalTripBooking(EducationalTripRequest tripRequest,
                                  String pnr,
                                  Integer allocatedBuses,
                                  BigDecimal totalFare,
                                  BookingStatus bookingStatus) {
        this.tripRequest = tripRequest;
        this.pnr = pnr;
        this.allocatedBuses = allocatedBuses;
        this.totalFare = totalFare;
        this.bookingStatus = bookingStatus;
    }

    @PrePersist
    protected void onCreate() {
        this.bookedAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
