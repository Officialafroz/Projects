package com.elevata.ecommerce.exception;

import com.elevata.ecommerce.dto.ErrorResponse;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationErrors(MethodArgumentNotValidException exception) {
        Map<String, String> errors = new HashMap<>();

        exception.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage()));

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }

    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<ErrorResponse> handleDataAccessException(DataAccessException exception) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResponse(LocalDateTime.now() ,
                        "Database error",
                        List.of(exception.getMostSpecificCause().getMessage())));
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFound(ResourceNotFoundException exception) {
        return errorWithNotFoundStatus(exception);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFound(UserNotFoundException exception) {
        return errorWithNotFoundStatus(exception);
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleProductNotFound(ProductNotFoundException exception) {
        return errorWithNotFoundStatus(exception);
    }

    @ExceptionHandler(StoreNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleStoreNotFound(StoreNotFoundException exception) {
        return errorWithNotFoundStatus(exception);
    }

    @ExceptionHandler(SellerNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleSellerNotFound(SellerNotFoundException exception) {
        return errorWithNotFoundStatus(exception);
    }

    private ResponseEntity<ErrorResponse> errorWithNotFoundStatus(Exception exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse(LocalDateTime.now(), exception.getMessage()));
    }
}
