package com.elevata.ecommerce.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
public class AddStoreDto {
    @Positive(message = "Vendor id must be positive.")
    private int vendorId;

    @NotNull
    private String name;

    @NotNull
    private String addressLine;

    @NotNull
    private String city;

    @NotNull
    private String state;

    @NotNull
    private String country;

    @Pattern(regexp = "^[1-9][0-9]{5}$", message = "Invalid postal code. Must begin with 1 to 9 and have 6 digits.")
    private String postalCode;

    @Pattern(regexp = "^(\\+91[\\-\\s]?)?[6-9]\\d{9}$", message = "Invalid contact number.")
    private String contactNumber;
}
