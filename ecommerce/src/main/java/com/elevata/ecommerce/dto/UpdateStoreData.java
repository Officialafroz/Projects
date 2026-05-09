package com.elevata.ecommerce.dto;

import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class UpdateStoreData {

    private String addressLine;
    private String city;

    @Pattern(regexp = "^[1-9][0-9]{5}$", message = "Invalid postal code. Must begin with 1 to 9 and have 6 digits.")
    private String postalCode;

    @Pattern(regexp = "^(\\+91[\\-\\s]?)?[6-9]\\d{9}$", message = "Invalid contact number.")
    private String contactNumber;
}
