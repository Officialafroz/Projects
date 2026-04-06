package com.elevata.gsrtc.dto;

import com.elevata.gsrtc.entity.BusDepot;
import lombok.*;

@Data
public class DepotAdminResponse {

    private long adminId;
    private String name;
    private String email;
    private String password;
    private String gender;
    private String phoneNumber;
    private String role;
    private String accountStatus;
    private BusDepot busDepot;

    public DepotAdminResponse(long adminId, String name, String email, String gender, String phoneNumber, String role, String accountStatus, BusDepot busDepot) {
        this.adminId = adminId;
        this.name = name;
        this.email = email;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
        this.role = role;
        this.accountStatus = accountStatus;
        this.busDepot = busDepot;
    }
}
