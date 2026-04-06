package com.elevata.gsrtc.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class DepotDTO {
    private int depotId;
    private String depotName;
    private String city;
    private String email;
    private String password;
    private String address;

    public DepotDTO(int depotId, String depotName, String city, String email, String address) {
        this.depotId = depotId;
        this.depotName = depotName;
        this.city = city;
        this.email = email;
        this.address = address;
    }
}
