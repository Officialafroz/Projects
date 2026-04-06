package com.elevata.gsrtc.controller;

import com.elevata.gsrtc.dto.CreateDepotAdminDTO;
import com.elevata.gsrtc.service.DepotAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/depot-admin")
public class DepotAdminController {

    private final DepotAdminService depotAdminService;

    @Autowired
    public DepotAdminController(DepotAdminService depotAdminService) {
        this.depotAdminService = depotAdminService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody CreateDepotAdminDTO dto) {
        return ResponseEntity.ok(depotAdminService.register(dto));
    }
}
