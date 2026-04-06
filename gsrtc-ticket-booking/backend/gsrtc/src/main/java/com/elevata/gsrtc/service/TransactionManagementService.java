package com.elevata.gsrtc.service;

import com.elevata.gsrtc.dto.TransactionManagementDTO;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public interface TransactionManagementService {
    List<TransactionManagementDTO> search(
            int depotId,
            String pnr,
            String transactionRef,
            LocalDateTime date,
            String paymentMethod,
            String status
    );
}
