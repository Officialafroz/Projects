package com.elevata.ecommerce.dto;

import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UpdateSeller {

    @Pattern(regexp = "^[6-9][0-9]{9}$", message = "Invalid mobile number")
    private String phoneNumber;

    @Pattern(regexp = "^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}",
             flags = Pattern.Flag.CASE_INSENSITIVE,
             message = "Invalid email")
    private String email;

    private String address;
}
