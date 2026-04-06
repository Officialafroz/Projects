package com.elevata.gsrtc.dto;

import com.elevata.gsrtc.enums.Ownership;
import lombok.Data;

@Data
public class InstituteDTO {
    private int instituteId;
    private String instituteCode;
    private String instituteName;
    private String instituteType;
    private Ownership ownership;
    private String location;
    private String institutePhone;
    private String instituteEmail;

    public InstituteDTO(int instituteId, String instituteCode, String instituteName, String instituteType, Ownership ownership, String location, String institutePhone, String instituteEmail) {
        this.instituteId = instituteId;
        this.instituteCode = instituteCode;
        this.instituteName = instituteName;
        this.instituteType = instituteType;
        this.ownership = ownership;
        this.location = location;
        this.institutePhone = institutePhone;
        this.instituteEmail = instituteEmail;
    }

    @Override
    public String toString() {
        return "InstituteDTO{" +
                instituteCode + '\'' +
                instituteName + '\'' +
                instituteType + '\'' +
                ownership + '\'' +
                location + '\'' +
                institutePhone + '\'' +
                instituteEmail + '\'' +
                '}';
    }
}
