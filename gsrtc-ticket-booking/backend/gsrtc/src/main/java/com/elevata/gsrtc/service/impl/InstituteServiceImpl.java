package com.elevata.gsrtc.service.impl;

import com.elevata.gsrtc.dto.InstituteDTO;
import com.elevata.gsrtc.dto.InstituteRegistrationDTO;
import com.elevata.gsrtc.entity.InstituteRegistry;
import com.elevata.gsrtc.enums.Ownership;
import com.elevata.gsrtc.exception.InstituteNotFoundException;
import com.elevata.gsrtc.repository.InstituteRepository;
import com.elevata.gsrtc.service.InstituteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class InstituteServiceImpl implements InstituteService {
    private final InstituteRepository instituteRepository;

    @Autowired
    public InstituteServiceImpl(com.elevata.gsrtc.repository.InstituteRepository instituteRepository) {
        this.instituteRepository = instituteRepository;
    }

    @Override
    public InstituteDTO verify(String code) {
        String udiseRegex = "^\\d{11}$";
        String aisheRegex = "(?i)^AIS\\d{5}$";

        if (code.matches(udiseRegex) || code.matches(aisheRegex)) {
            return instituteRepository.fetchInstituteDetails(code)
                    .orElseThrow(() ->
                            new InstituteNotFoundException("Institute is not registered."));
        }

        throw new RuntimeException("Invalid code format " + code);
    }

    @Transactional
    @Override
    public InstituteRegistry registerInstitute(InstituteRegistrationDTO dto) {

        InstituteRegistry institute = new InstituteRegistry();
        institute.setInstituteName(dto.getInstituteName());
        institute.setInstituteType(dto.getInstituteType());
        institute.setOwnership(Ownership.valueOf(dto.getOwnership()));
        institute.setLocation(dto.getLocation());
        institute.setInstitutePhone(dto.getInstitutePhone());
        institute.setInstituteEmail(dto.getInstituteEmail());

        // auto-generate code
        institute.setInstituteCode(
                generateInstituteCode(dto.getInstituteType())
        );

        return instituteRepository.save(institute);
    }


    @Override
    public InstituteRegistry getInstituteByCode(String instituteCode) {
        return instituteRepository.findByInstituteCode(instituteCode)
                .orElseThrow(() ->
                        new RuntimeException("Institute not found with code: " + instituteCode));
    }

    private String generateInstituteCode(String instituteType) {

        if ("SCHOOL".equalsIgnoreCase(instituteType)) {
            return generateUdiseCode();   // 11 digits
        }
        else if ("COLLEGE".equalsIgnoreCase(instituteType)) {
            return generateAisheCode();   // 8 characters
        }
        else {
            throw new IllegalArgumentException("Invalid institute type");
        }
    }

    private String generateUdiseCode() {
        String udise;
        do {
            udise = String.valueOf(
                    10000000000L + (long) (Math.random() * 90000000000L)
            );
        } while (instituteRepository.existsByInstituteCode(udise));

        return udise;
    }

    private String generateAisheCode() {
        String aishe;
        do {
            aishe = "AIS" + (int) (10000 + Math.random() * 90000); // e.g. AIS12345
        } while (instituteRepository.existsByInstituteCode(aishe));

        return aishe;
    }
}
