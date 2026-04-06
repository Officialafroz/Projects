package com.elevata.gsrtc.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@AllArgsConstructor
@Getter @Setter
@Data
public class EduPaymentDTO {
    private int requestId;
    private BigDecimal amount;
    private String method;
    private String transactionRef;
    private LocalDateTime paymentDate;
}
