package com.elevata.gsrtc.dto;

import lombok.Data;

@Data
public class CreateDepotAdminDTO {
    private String token;
    private String name;
    private String email;
    private String phoneNumber;
    private String gender;
    private String address;
//    private String password;
}
