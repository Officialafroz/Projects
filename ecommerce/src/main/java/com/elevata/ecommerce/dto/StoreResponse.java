package com.elevata.ecommerce.dto;

import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class StoreResponse {
    private int storeId;
    private UserDto vendor;
    private String name;

    //Concatenation of address line, city, state and country
    private String address;

    private int postalCode;
    private String contactNumber;
    private boolean isActive;
}
