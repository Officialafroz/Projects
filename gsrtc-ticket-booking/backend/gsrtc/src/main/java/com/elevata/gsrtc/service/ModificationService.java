package com.elevata.gsrtc.service;

import com.elevata.gsrtc.entity.AppUser;
import com.elevata.gsrtc.entity.BusDepot;
import com.elevata.gsrtc.entity.DepotAdminInvite;
import com.elevata.gsrtc.entity.RefreshToken;
import com.elevata.gsrtc.repository.AppUserRepository;
import com.elevata.gsrtc.repository.BusDepotRepository;
import com.elevata.gsrtc.repository.DAInviteRepository;
import com.elevata.gsrtc.repository.RefreshTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ModificationService {
    private AppUserRepository userRepository;
    private BusDepotRepository depotRepository;
    private DAInviteRepository inviteRepository;
    private RefreshTokenRepository tokenRepository;

    @Autowired
    public ModificationService(AppUserRepository userRepository, BusDepotRepository depotRepository, DAInviteRepository inviteRepository, RefreshTokenRepository tokenRepository) {
        this.userRepository = userRepository;
        this.depotRepository = depotRepository;
        this.inviteRepository = inviteRepository;
        this.tokenRepository = tokenRepository;
    }

    @Transactional
    public String delete(int depotId) {
        BusDepot depot = depotRepository.findById(depotId).orElseThrow();
        AppUser user = userRepository.findByBusDepotDepotId(depotId)
                .orElseThrow(() -> new RuntimeException("user not found for id " + depotId));
        DepotAdminInvite invite = inviteRepository.findByBusDepotDepotId(depotId);
        RefreshToken token = tokenRepository.findByUser(user)
                .orElseThrow();

        if (invite != null) {
            invite.setBusDepot(null);
            inviteRepository.save(invite);
            inviteRepository.delete(invite);
        }

        token.setUser(null);
        tokenRepository.save(token);
        tokenRepository.delete(token);

        user.setBusDepot(null);
        userRepository.save(user);
        userRepository.delete(user);

//        depotRepository.delete(depot);

        return "depot and user deleted.";
    }
}
