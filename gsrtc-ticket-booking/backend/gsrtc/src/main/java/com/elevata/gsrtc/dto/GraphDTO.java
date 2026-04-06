package com.elevata.gsrtc.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Month;

@NoArgsConstructor
@Data
public class GraphDTO {

    private String label;
    private Number value;

    public GraphDTO(Integer label, Number value) {
        this.label = Month.of(label).name();
        this.value = value;
    }
}
