package com.elevata.ecommerce.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
public class UserDto {
        int userId;
        String name;
        String phoneNumber;
        int pincode;
        String address;
}
