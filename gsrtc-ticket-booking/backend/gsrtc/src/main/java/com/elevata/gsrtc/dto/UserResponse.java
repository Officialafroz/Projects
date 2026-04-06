package com.elevata.gsrtc.dto;

import lombok.*;

@Data
public class UserResponse {
    private long userId;
    private String name;
    private String email;
    private String role;
    private int depotId;
}
