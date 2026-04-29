package com.elevata.ecommerce.dto;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter @Setter
@AllArgsConstructor
public class AddProductDto {
    private String name;
    private String description;

    @Pattern(regexp = "^[0-9]$", message = "Invalid stock")
    @Size(min = 0, message = "Invalid stock length")
    private int stock;

    @Pattern(regexp = "^\\d+(\\.\\d+)?$")
    private BigDecimal price;
}
