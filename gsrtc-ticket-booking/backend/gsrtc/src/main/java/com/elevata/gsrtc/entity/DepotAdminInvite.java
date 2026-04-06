package com.elevata.gsrtc.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Table
@Entity(name = "depot_admin_invite")
public class DepotAdminInvite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "invite_id")
    private long inviteId;

    @Column(name = "token")
    private String token;

    @Column(name = "email")
    private String email;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "depot_id")
    private BusDepot busDepot;

    @Column(name = "used")
    private boolean used;

    @Column(name = "expiry_time")
    private LocalDateTime expiryTime;
}
