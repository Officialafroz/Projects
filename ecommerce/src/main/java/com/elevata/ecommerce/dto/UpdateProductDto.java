package com.elevata.ecommerce.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class UpdateProductDto {

    @PositiveOrZero(message = "Invalid stock length")
    private Integer stock;

    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be positive.")
    private BigDecimal price;
}
