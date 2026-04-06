package com.elevata.gsrtc.repository;

import com.elevata.gsrtc.entity.DepotAdminInvite;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DAInviteRepository extends JpaRepository<DepotAdminInvite, Long> {
    Optional<DepotAdminInvite> findByToken(String token);

    DepotAdminInvite findByBusDepotDepotId(int depotId);
}
