package com.elevata.gsrtc.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter @Setter
@Data
public class TransactionManagementDTO {
    private String pnr;
    private String transactionRef;
    private double amount;
    private String paymentMethod;
    private LocalDateTime paymentDate;
    private String status;


    public TransactionManagementDTO(String pnr, String transactionRef, double amount, String paymentMethod, LocalDateTime paymentDate, String status) {
        this.pnr = pnr;
        this.transactionRef = transactionRef;
        this.amount = amount;
        this.paymentMethod = paymentMethod;
        this.paymentDate = paymentDate;
        this.status = status;
    }
}
