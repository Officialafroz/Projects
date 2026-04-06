package com.elevata.gsrtc.dto;

import com.elevata.gsrtc.enums.AccountStatus;
import lombok.*;

@Data
public class DepotAdminResponseDTO {
    private long depotAdminId;
    private String name;
    private String email;
    private String phoneNumber;
    private String depotName;
    private String address;
    private String accountStatus;

    public DepotAdminResponseDTO(long depotAdminId, String name, String email, String phoneNumber, String depotName, String address, AccountStatus accountStatus) {
        this.depotAdminId = depotAdminId;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.depotName = depotName;
        this.address = address;
        this.accountStatus = accountStatus.name();
    }
}
