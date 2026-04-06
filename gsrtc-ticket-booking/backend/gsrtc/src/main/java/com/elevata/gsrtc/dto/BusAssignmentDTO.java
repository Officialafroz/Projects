package com.elevata.gsrtc.dto;

import com.elevata.gsrtc.entity.Bus;
import com.elevata.gsrtc.entity.Staff;
import com.elevata.gsrtc.enums.BusAssignmentStatus;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class BusAssignmentDTO {
    private Bus bus;
    private Staff driver;
    private Staff conductor;
    private BusAssignmentStatus status;
}
