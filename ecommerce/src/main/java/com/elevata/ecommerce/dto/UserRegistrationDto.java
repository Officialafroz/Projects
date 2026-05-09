package com.elevata.ecommerce.dto;

import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class UserRegistrationDto {
    private String name;

    @Pattern(regexp = "^[6-9][0-9]{9}$", message = "Invalid mobile number")
    private String phoneNumber;

    @Pattern(regexp = "^[1-9][0-9]{5}$", message = "Invalid postal code. Must begin with 1 to 9 and have 6 digits.")
    private String pincode;

    private String address;
}
