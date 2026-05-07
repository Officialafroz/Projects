package com.elevata.ecommerce.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter @Setter
@Builder
public class ProductResponse {

    private int productId;
    private String name;
    private String description;
    private int stock;
    private BigDecimal price;
}
