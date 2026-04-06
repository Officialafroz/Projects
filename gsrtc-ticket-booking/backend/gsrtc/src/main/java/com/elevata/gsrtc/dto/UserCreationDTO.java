package com.elevata.gsrtc.dto;

import lombok.Data;

@Data
public class UserCreationDTO {
    private String name;
    private String email;
    private String phoneNumber;
    private String gender;
}
