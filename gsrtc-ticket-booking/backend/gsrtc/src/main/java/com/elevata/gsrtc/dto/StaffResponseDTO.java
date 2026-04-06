package com.elevata.gsrtc.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class StaffResponseDTO {
    private String fullName;
    private String stf;
    private String mobileNumber;
    private String role;
    private String licenseNumber;
    private String depotName;

    public StaffResponseDTO(String fullName, String stf, String mobileNumber, String role, String licenseNumber, String depotName) {
        this.fullName = fullName;
        this.stf = stf;
        this.mobileNumber = mobileNumber;
        this.role = role;
        this.licenseNumber = licenseNumber;
        this.depotName = depotName;
    }
}
