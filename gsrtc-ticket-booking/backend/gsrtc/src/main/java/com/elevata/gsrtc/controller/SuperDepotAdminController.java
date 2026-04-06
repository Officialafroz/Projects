package com.elevata.gsrtc.controller;

import com.elevata.gsrtc.dto.*;
import com.elevata.gsrtc.service.SuperDepotAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/central/depot-admin")
public class SuperDepotAdminController {

    private final SuperDepotAdminService superDepotAdminService;

    @Autowired
    public SuperDepotAdminController(SuperDepotAdminService superDepotAdminService) {
        this.superDepotAdminService = superDepotAdminService;
    }

    @PostMapping("/list")
    public ResponseEntity<Page<DepotAdminResponseDTO>> getDepotAdminsList(
            @RequestParam(defaultValue = "0") int pageNo,
            @RequestParam(defaultValue = "10") int pageSize
    ) {
        Page<DepotAdminResponseDTO> dAList = superDepotAdminService
                .getDepotAdminList(pageNo, pageSize);

        return ResponseEntity.ok(dAList);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> update(
            @PathVariable Long id,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String phoneNumber) {

        superDepotAdminService.update(id, email, phoneNumber);
        return ResponseEntity.ok("Depot Admin updated successfully.");
    }

    @PostMapping("/invite")
    public ResponseEntity<String> inviteDepotAdmin(@RequestBody InviteDepotAdminDTO dto) {

        superDepotAdminService.generateInvite(dto);
        return ResponseEntity.ok("Invite link sent successfully.");
    }

    @PostMapping("/search")
    public Page<DepotAdminResponseDTO> search(
            @RequestParam String keyword,
            Pageable pageable) {

        return superDepotAdminService.search(keyword, pageable);
    }

    @PostMapping("/credentials/{id}")
    public ResponseEntity<String> sendMail(
            @PathVariable long id,
            @RequestParam String email,
            @RequestParam String password
    ) {
        return ResponseEntity.ok(superDepotAdminService.sendMail(id, email, password));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteDepotAdmin(@PathVariable Long id) {

        superDepotAdminService.deleteById(id);
        return ResponseEntity.ok("Depot Admin deleted successfully.");
    }


    @GetMapping("/{id}")
    public ResponseEntity<DepotAdminResponse> findById(@PathVariable int adminId) {
        return ResponseEntity.ok(superDepotAdminService.findById(adminId));
    }

    @GetMapping("getAdmin/{email}")
    public ResponseEntity<DepotAdminDTO> findByEmail(@PathVariable String email) {
        return ResponseEntity.ok(superDepotAdminService.findByEmail(email));
    }

    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<String> deleteById(@PathVariable long userId) {

        if (userId <= 0) throw new RuntimeException("User id is invalid.");

        superDepotAdminService.deleteById(userId);
        return ResponseEntity.ok(String.format("User for id %d is deleted.", userId));
    }
}
