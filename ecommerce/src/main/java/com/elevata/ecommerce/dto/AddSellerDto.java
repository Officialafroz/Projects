package com.elevata.ecommerce.dto;

import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
public class AddSellerDto {

    private String fullName;

    @Pattern(regexp = "^[6-9][0-9]{9}$", message = "Invalid mobile number")
    private String phoneNumber;

    @Pattern(regexp = "^[a-zA-Z0-9._%+\\-]+@[a-zA-Z0-9.\\-]+\\.[a-zA-Z]{2,}$",
             flags = Pattern.Flag.CASE_INSENSITIVE,
             message = "Invalid email")
    private String email;

    private String address;
}
