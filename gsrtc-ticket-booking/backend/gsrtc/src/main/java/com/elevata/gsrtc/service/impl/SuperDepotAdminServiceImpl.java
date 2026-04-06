package com.elevata.gsrtc.service.impl;

import com.elevata.gsrtc.dto.*;
import com.elevata.gsrtc.entity.AppUser;
import com.elevata.gsrtc.entity.BusDepot;
import com.elevata.gsrtc.entity.DepotAdminInvite;
import com.elevata.gsrtc.enums.AccountStatus;
import com.elevata.gsrtc.enums.Role;
import com.elevata.gsrtc.repository.AppUserRepository;
import com.elevata.gsrtc.repository.BusDepotRepository;
import com.elevata.gsrtc.repository.DAInviteRepository;
import com.elevata.gsrtc.service.EmailService;
import com.elevata.gsrtc.service.SuperDepotAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class SuperDepotAdminServiceImpl implements SuperDepotAdminService {
    private final AppUserRepository userRepository;
    private final BusDepotRepository depotRepository;
    private final DAInviteRepository daInviteRepository;
    private final EmailService emailService;

    @Autowired
    public SuperDepotAdminServiceImpl(AppUserRepository userRepository, BusDepotRepository depotRepository, DAInviteRepository daInviteRepository, EmailService emailService) {
        this.userRepository = userRepository;
        this.depotRepository = depotRepository;
        this.daInviteRepository = daInviteRepository;
        this.emailService = emailService;
    }

    @Override
    public Page<DepotAdminResponseDTO> getDepotAdminList(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by("createdAt").ascending());
        return userRepository.getDepotAdminList(pageable);
    }

    @Transactional
    @Override
    public void update(Long id, String email, String phoneNumber) {

        AppUser user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Depot Admin not found"));

        if (!user.getRole().equals(Role.DEPOT_ADMIN)) {
            throw new RuntimeException("User is not a Depot Admin");
        }

        if (!email.isBlank()) {
            user.setEmail(email);
        }

        if (!phoneNumber.isBlank()) {
            user.setPhoneNumber(phoneNumber);
        }

        userRepository.save(user);
    }

    @Transactional
    @Override
    public void generateInvite(InviteDepotAdminDTO dto) {
        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new RuntimeException("User already exists with this email.");
        }

        BusDepot depot = depotRepository.findById(dto.getDepotId())
                .orElseThrow(() -> new RuntimeException("Depot not found"));

        String token = UUID.randomUUID().toString();

        DepotAdminInvite invite = new DepotAdminInvite();
        invite.setEmail(dto.getEmail());
        invite.setToken(token);
        invite.setBusDepot(depot);
        invite.setUsed(false);
        invite.setExpiryTime(LocalDateTime.now().plusDays(1));

        daInviteRepository.save(invite);

        String link = "http://localhost:5173/depot-admin/register?token=" + token;
        String text = """
                Hello,
                You have been invited to become a depot admin.
                Click the link below to create your account.
                
                """;

        emailService.sendEmail(dto.getEmail(), "Admin registration link", text + link);
    }

    @Override
    public Page<DepotAdminResponseDTO> search(String keyword, Pageable pageable) {

        Page<AppUser> users = userRepository.searchDepotAdmins(keyword, pageable);

        return users.map(user -> {

            BusDepot depot = user.getBusDepot();
            return new DepotAdminResponseDTO(
                    user.getUserId(),
                    user.getName(),
                    user.getEmail(),
                    user.getPhoneNumber(),
                    depot.getDepotName(),
                    depot.getAddress(),
                    user.getAccountStatus()
            );
        });
    }

    @Transactional
    @Override
    public void deleteById(Long id) {

        AppUser user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Depot Admin not found"));

        if (!user.getRole().equals(Role.DEPOT_ADMIN)) {
            throw new RuntimeException("User is not a Depot Admin");
        }

        user.setBusDepot(null); // remove association first
        userRepository.delete(user);
    }

    @Override
    public DepotAdminResponse findById(long adminId) {

        AppUser user = userRepository.findById(adminId)
                .orElseThrow(() ->new RuntimeException("Depot admin not found for id: " + adminId));

        return new DepotAdminResponse(
                user.getUserId(),
                user.getName(),
                user.getEmail(),
                user.getGender(),
                user.getPhoneNumber(),
                user.getRole().name(),
                user.getAccountStatus().name(),
                user.getBusDepot()
        );
    }

    @Override
    public DepotAdminDTO findByEmail(String email) {
        AppUser existingAdmin = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Depot admin not found or email invalid"));

        return new DepotAdminDTO(
                existingAdmin.getName(), existingAdmin.getBusDepot().getDepotId());
    }

    @Override
    public String sendMail(long adminId, String email, String password) {

        AppUser existing = userRepository.findById(adminId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid admin id " + adminId));

        String text = String.format("""
                Account login email - %s and password %s.
                
                Note: Do not share this with other persons.
                """, email, password);
        emailService.sendEmail(existing.getEmail(), "Login credentials", text);

        existing.setAccountStatus(AccountStatus.ACTIVE);

        userRepository.save(existing);

        return "Depot account login credentials sent to email " + existing.getEmail();
    }
}
