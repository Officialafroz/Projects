package com.elevata.gsrtc.service;

import com.elevata.gsrtc.dto.CreateDepotAdminDTO;
import org.springframework.stereotype.Service;

@Service
public interface DepotAdminService {
    String register(CreateDepotAdminDTO dto);
}
