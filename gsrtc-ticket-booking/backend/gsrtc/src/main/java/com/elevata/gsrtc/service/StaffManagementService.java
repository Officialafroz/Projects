package com.elevata.gsrtc.service;

import com.elevata.gsrtc.dto.StaffManagementDTO;
import com.elevata.gsrtc.dto.StaffCreationDTO;
import com.elevata.gsrtc.dto.StaffResponseDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface StaffManagementService {
    String addStaff(StaffCreationDTO dto);

    List<StaffResponseDTO> findAllStaffMembers(int depotId);

    StaffResponseDTO findByStf(String stf);

    List<StaffManagementDTO> searchStaff(
            int depotId,
            String stf,
            String fullName,
            String licenseNumber,
            String role,
            String status
    );

    String getDepotName(String stf);
}
