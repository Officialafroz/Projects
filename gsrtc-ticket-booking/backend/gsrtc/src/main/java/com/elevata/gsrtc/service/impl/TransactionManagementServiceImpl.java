package com.elevata.gsrtc.service.impl;

import com.elevata.gsrtc.dto.TransactionManagementDTO;
import com.elevata.gsrtc.repository.PaymentRepository;
import com.elevata.gsrtc.service.TransactionManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TransactionManagementServiceImpl implements TransactionManagementService {
    private final PaymentRepository paymentRepository;

    @Autowired
    public TransactionManagementServiceImpl(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    @Override
    public List<TransactionManagementDTO> search(
            int depotId,
            String pnr,
            String transactionRef,
            LocalDateTime date,
            String paymentMethod,
            String status
    ) {

        return paymentRepository.filterDepotTransactions(depotId, pnr, transactionRef, date, paymentMethod, status);
    }
}
