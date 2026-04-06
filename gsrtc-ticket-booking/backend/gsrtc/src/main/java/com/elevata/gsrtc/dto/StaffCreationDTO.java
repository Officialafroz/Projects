package com.elevata.gsrtc.dto;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Data
public class StaffCreationDTO {
    private String fullName;
    private String mobileNumber;
    private String role;
    private String licenseNumber;
    private int depotId;
}
