package com.elevata.ecommerce.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class ErrorResponse {
    private LocalDateTime timestamp;
    private String message;
    private List<String> details;

    public ErrorResponse(LocalDateTime timestamp, String message, List<String> details) {
        this(timestamp, message);
        this.details = details;
    }

    public ErrorResponse(LocalDateTime timestamp, String message) {
        this.timestamp = timestamp;
        this.message = message;
    }
}
