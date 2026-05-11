package com.elevata.ecommerce.controller;

import com.elevata.ecommerce.dto.*;
import com.elevata.ecommerce.service.SellerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/seller")
public class SellerController {
    private final SellerService sellerService;

    @Autowired
    public SellerController(SellerService sellerService) {
        this.sellerService = sellerService;
    }

    @PostMapping
    public ResponseEntity<String> save(@Valid @RequestBody AddSellerDto dto) {
        String message = sellerService.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(message);
    }

    @GetMapping("/page")
    public ResponseEntity<Page<SellerResponse>> getSellersListPage(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Page<SellerResponse> responses = sellerService.getSellersListPage(page, size);
        return ResponseEntity.status(HttpStatus.FOUND).body(responses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SellerResponse> findById(@PathVariable int id) {
        SellerResponse response = sellerService.findById(id);
        return ResponseEntity.status(HttpStatus.FOUND).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SellerResponse> updateById(
            @PathVariable int id, @Valid @RequestBody UpdateSeller dto
    ) {
        SellerResponse response = sellerService.updateById(id, dto);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteById(@PathVariable int id) {
        String message = sellerService.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).body(message);
    }
}
