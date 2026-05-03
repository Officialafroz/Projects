package com.elevata.ecommerce.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter @Setter
@AllArgsConstructor
public class AddProductDto {
    private String name;
    private String description;

    @PositiveOrZero(message = "Invalid stock length")
    private int stock;

    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be positive.")
    private BigDecimal price;
}
