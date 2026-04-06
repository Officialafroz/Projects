package com.elevata.gsrtc.service.impl;

import com.elevata.gsrtc.dto.StaffManagementDTO;
import com.elevata.gsrtc.dto.StaffCreationDTO;
import com.elevata.gsrtc.dto.StaffResponseDTO;
import com.elevata.gsrtc.entity.BusDepot;
import com.elevata.gsrtc.entity.Staff;
import com.elevata.gsrtc.enums.EmploymentStatus;
import com.elevata.gsrtc.enums.StaffRole;
import com.elevata.gsrtc.repository.BusDepotRepository;
import com.elevata.gsrtc.repository.StaffRepository;
import com.elevata.gsrtc.service.ReferenceNumberService;
import com.elevata.gsrtc.service.StaffManagementService;
import com.elevata.gsrtc.util.StringNormalizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class StaffManagementServiceImpl implements StaffManagementService {
    private StaffRepository staffRepository;
    private BusDepotRepository depotRepository;

    @Autowired
    public StaffManagementServiceImpl(StaffRepository staffRepository, BusDepotRepository depotRepository) {
        this.staffRepository = staffRepository;
        this.depotRepository = depotRepository;
    }

    @Transactional
    @Override
    public String addStaff(StaffCreationDTO dto) {
        Staff staff = new Staff();

        Optional<Staff> staff1  = staffRepository.findTopByOrderByCreatedAtDesc();
        if (staff1.isPresent()) {
            String stf = staff1.get().getStf();
            System.out.println(stf);

            staff.setStf(ReferenceNumberService.generateStf(Objects.requireNonNullElse(stf, "STF00000")));
        }

        staff.setFullName(dto.getFullName());
        staff.setMobileNumber(dto.getMobileNumber());

        staff.setRole(switch (dto.getRole()) {
            case "DRIVER" -> StaffRole.DRIVER;
            case "CONDUCTOR" -> StaffRole.CONDUCTOR;
            default -> throw new RuntimeException("Invalid role.");
        });

        if (dto.getLicenseNumber() != null) {
            staff.setLicenseNumber(dto.getLicenseNumber().trim());
        } else {
            throw new RuntimeException("License number is empty.");
        }

        staff.setStatus(EmploymentStatus.ON_LEAVE);

        BusDepot depot = depotRepository.findById(dto.getDepotId())
                .orElseThrow(() -> new RuntimeException("Bus depot not found"));
        System.out.println(depot);
        staff.setBusDepot(depot);

        staffRepository.save(staff);

        return "Staff added to DB.";
    }

    @Override
    public List<StaffResponseDTO> findAllStaffMembers(int depotId) {
        return staffRepository.findAll()
                .stream()
                .filter(staff -> staff.getBusDepot().getDepotId() == depotId)
                .map(staff -> {
                    StaffResponseDTO dto = new StaffResponseDTO();

                    dto.setStf(staff.getStf());
                    dto.setFullName(staff.getFullName());
                    dto.setMobileNumber(staff.getMobileNumber());
                    dto.setRole(staff.getRole().name());
                    if (staff.getLicenseNumber() != null) {
                        dto.setLicenseNumber(staff.getLicenseNumber());
                    }
                    dto.setDepotName(staff.getBusDepot().getDepotName());

                    return dto;
                }).toList();
    }

    @Override
    public StaffResponseDTO findByStf(String stf) {
        Staff staff = staffRepository.findByStf(stf);

        return new StaffResponseDTO(
                staff.getFullName(),
                staff.getStf(),
                staff.getMobileNumber(),
                staff.getRole().name(),
                staff.getLicenseNumber(),
                staff.getBusDepot().getDepotName()
        );
    }

    @Override
    public List<StaffManagementDTO> searchStaff(
            int depotId,
            String stf,
            String fullName,
            String licenseNumber,
            String role,
            String status
    ) {

        stf = StringNormalizer.normalize(stf);
        fullName = StringNormalizer.normalize(fullName);
        role = StringNormalizer.normalize(role);
        licenseNumber = StringNormalizer.normalize(licenseNumber);
        status = StringNormalizer.normalize(status);

        return staffRepository
                .searchStaff(
                        depotId,
                        stf,
                        fullName,
                        role,
                        licenseNumber,
                        status != null ? EmploymentStatus.getEmploymentStatus(status) : null
                );
    }

    @Override
    public String getDepotName(String stf) {
        return staffRepository.findByStf(stf).getBusDepot().getDepotName();
    }

    private String getRole(Staff staff) {

        if (staff.getRole().name().equals("DRIVER")) {
            return "DRIVER";
        }

        return "CONDUCTOR";
    }
}
