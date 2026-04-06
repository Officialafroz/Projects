package com.elevata.gsrtc.dto;

import com.elevata.gsrtc.enums.EmploymentStatus;
import com.elevata.gsrtc.enums.StaffRole;

public class StaffManagementDTO {
    private String fullName;
    private String stf;
    private String mobileNumber;
    private String licenseNumber;
    private StaffRole role;
    private EmploymentStatus status;
    private String depotName;

    public StaffManagementDTO(String fullName, String stf, String mobileNumber, String licenseNumber, StaffRole role, EmploymentStatus status, String name) {
        this.fullName = fullName;
        this.stf = stf;
        this.mobileNumber = mobileNumber;
        this.licenseNumber = licenseNumber;
        this.role = role;
        this.status = status;
    }
}
