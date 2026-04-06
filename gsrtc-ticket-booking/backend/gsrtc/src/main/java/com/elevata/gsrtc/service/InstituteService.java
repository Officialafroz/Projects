package com.elevata.gsrtc.service;

import com.elevata.gsrtc.dto.InstituteDTO;
import com.elevata.gsrtc.dto.InstituteRegistrationDTO;
import com.elevata.gsrtc.entity.InstituteRegistry;
import org.springframework.stereotype.Service;

@Service
public interface InstituteService {
    InstituteDTO verify(String code);

    InstituteRegistry registerInstitute(InstituteRegistrationDTO dto);

    InstituteRegistry getInstituteByCode(String instituteCode);
}
