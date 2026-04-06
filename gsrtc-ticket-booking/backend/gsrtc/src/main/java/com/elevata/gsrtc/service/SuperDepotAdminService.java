package com.elevata.gsrtc.service;

import com.elevata.gsrtc.dto.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public interface SuperDepotAdminService {

    Page<DepotAdminResponseDTO> getDepotAdminList(int pageNo, int pageSize);

    void generateInvite(InviteDepotAdminDTO dto);

    void update(Long id, String email, String phoneNumber);

    void deleteById(Long id);

    Page<DepotAdminResponseDTO> search(String keyword, Pageable pageable);

    DepotAdminResponse findById(long adminId);

    DepotAdminDTO findByEmail(String email);

    String sendMail(long adminId, String email, String password);
}
