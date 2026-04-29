package com.elevata.ecommerce.dto;

import lombok.Data;

@Data
public class UserRegistrationDto {
    private String name;
    //Regex
    private String phoneNumber;
    //Regex
    private int pincode;
    private String address;
}
