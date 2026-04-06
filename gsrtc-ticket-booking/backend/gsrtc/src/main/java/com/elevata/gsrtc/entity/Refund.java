package com.elevata.gsrtc.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@Data
@Entity
@Table(name = "refund")
public class Refund {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "refund_id")
    private int refundId;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "payment_id")
    private Payment payment;

    @Column(name = "refund_ref")
    private String refundRef;

    @Column(name = "refund_amount")
    private double refundAmount;

    @Column(name = "status")
    private String status;

    @Column(name = "refunded_at")
    private LocalDateTime refundedAt = LocalDateTime.now();

    @Column(name = "reason")
    private String reason;

    public Refund(String refundRef, double refundAmount, String status, String reason) {
        this.refundRef = refundRef;
        this.refundAmount = refundAmount;
        this.status = status;
        this.reason = reason;
    }
}
