package com.elevata.ecommerce.dto;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
public class UserDto {
    private int userId;

    @Pattern(regexp = "^[A-Za-z]\\w{5,29}$", message = "Invalid name")
    private String name;

    @Pattern(regexp = "^[6-9][0-9]{9}$", message = "Invalid mobile number")
    @Size(min = 10, max = 10, message = "Mobile number must be 10 digits")
    private String phoneNumber;

    @Pattern(regexp = "^[1-9][0-9]{5}$", message = "Invalid pincode")
    @Size(min = 6, max = 6, message = "Pincode must be 6 digits")
    private int pincode;

    private String address;
}
