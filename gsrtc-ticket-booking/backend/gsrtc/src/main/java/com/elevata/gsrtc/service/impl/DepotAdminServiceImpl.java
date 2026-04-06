package com.elevata.gsrtc.service.impl;

import com.elevata.gsrtc.dto.CreateDepotAdminDTO;
import com.elevata.gsrtc.entity.AppUser;
import com.elevata.gsrtc.entity.BusDepot;
import com.elevata.gsrtc.entity.DepotAdminInvite;
import com.elevata.gsrtc.enums.AccountStatus;
import com.elevata.gsrtc.enums.Role;
import com.elevata.gsrtc.repository.AppUserRepository;
import com.elevata.gsrtc.repository.BusDepotRepository;
import com.elevata.gsrtc.repository.DAInviteRepository;
import com.elevata.gsrtc.service.DepotAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class DepotAdminServiceImpl implements DepotAdminService {
    private final DAInviteRepository daInviteRepository;
    private final AppUserRepository userRepository;
    private final BusDepotRepository depotRepository;

    @Autowired
    public DepotAdminServiceImpl(DAInviteRepository daInviteRepository, AppUserRepository userRepository, BusDepotRepository depotRepository) {
        this.daInviteRepository = daInviteRepository;
        this.userRepository = userRepository;
        this.depotRepository = depotRepository;
    }

    @Override
    @Transactional
    public String register(CreateDepotAdminDTO dto) {
        DepotAdminInvite invite = daInviteRepository
                .findByToken(dto.getToken())
                .orElseThrow(() -> new RuntimeException("Invalid invite token"));

        if (invite.isUsed()) {
            throw new RuntimeException("This invite link has already been used");
        }

        if (invite.getExpiryTime().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Invite link expired.");
        }

        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new IllegalArgumentException("Email already exists");
        }

        BusDepot existingDepot = depotRepository.findById(invite.getBusDepot().getDepotId())
                .orElseThrow(() -> new RuntimeException("Depot not found."));

        AppUser user = new AppUser();
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setPhoneNumber(dto.getPhoneNumber());
        user.setGender(dto.getGender());
        user.setProvider("LOCALE");
        user.setRole(Role.DEPOT_ADMIN);
        user.setBusDepot(existingDepot);
        user.setAccountStatus(AccountStatus.PENDING);

        userRepository.save(user);

        invite.setUsed(true);

        daInviteRepository.save(invite);

        return "Account created successfully. " +
               "An email for login details will be sent shortly.";
    }
}
