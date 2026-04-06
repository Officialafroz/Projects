package com.elevata.gsrtc.dto;

import lombok.Data;

@Data
public class DepotDropDownDTO {
    private Integer depotId;
    private String depotName;

    public DepotDropDownDTO(Integer depotId, String depotName) {
        this.depotId = depotId;
        this.depotName = depotName;
    }
}
