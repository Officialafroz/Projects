package com.elevata.ecommerce.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter @Setter
@AllArgsConstructor
public class ProductResponse {

    private int productId;
    private String name;
    private String description;
    private int stock;
    private BigDecimal price;
}
