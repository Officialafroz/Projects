package com.elevata.gsrtc.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class InstituteRegistrationDTO {
    private String instituteName;
    private String instituteType;
    private String ownership;
    private String location;
    private String institutePhone;
    private String instituteEmail;
}
