package com.elevata.gsrtc.controller;

import com.elevata.gsrtc.dto.StaffManagementDTO;
import com.elevata.gsrtc.dto.StaffCreationDTO;
import com.elevata.gsrtc.dto.StaffResponseDTO;
import com.elevata.gsrtc.service.StaffManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/depot/staff-management/staff")
public class StaffManagementController {

    private final StaffManagementService staffManagementService;

    @Autowired
    public StaffManagementController(StaffManagementService staffManagementService) {
        this.staffManagementService = staffManagementService;
    }

    @PostMapping
    public ResponseEntity<String> addStaff(@RequestBody StaffCreationDTO dto) {
        String response = staffManagementService.addStaff(dto);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/list")
    public List<StaffResponseDTO> findAllStaffMembers(@RequestParam int depotId) {
        return staffManagementService.findAllStaffMembers(depotId);
    }

    @GetMapping("/{stf}")
    public StaffResponseDTO findByStf(@PathVariable String stf) {
        return staffManagementService.findByStf(stf);
    }

    @GetMapping("/search")
    public ResponseEntity<List<StaffManagementDTO>> searchStaff(
            @RequestParam int depotId,
            @RequestParam(required = false) String stf,
            @RequestParam(required = false) String fullName,
            @RequestParam(required = false) String licenseNumber,
            @RequestParam(required = false) String role,
            @RequestParam(required = false) String status
    ) {

        return ResponseEntity.ok(staffManagementService
                .searchStaff(depotId, stf, fullName, licenseNumber, role, status));
    }
}
