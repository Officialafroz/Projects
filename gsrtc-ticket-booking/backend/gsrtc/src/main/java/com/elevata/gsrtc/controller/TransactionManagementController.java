package com.elevata.gsrtc.controller;

import com.elevata.gsrtc.dto.TransactionManagementDTO;
import com.elevata.gsrtc.service.TransactionManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/depot/transaction-management")
public class TransactionManagementController {

    private final TransactionManagementService transactionService;

    @Autowired
    public TransactionManagementController(TransactionManagementService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping("/search")
    public ResponseEntity<List<TransactionManagementDTO>> searchTransactions(
            @RequestParam int depotId,
            @RequestParam(required = false) String pnr,
            @RequestParam(required = false) String transactionRef,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDateTime date,
            @RequestParam(required = false) String paymentMethod,
            @RequestParam(required = false) String status
    ) {
        return ResponseEntity.ok(transactionService.search(
                depotId, pnr, transactionRef, date, paymentMethod, status
        ));
    }
}
