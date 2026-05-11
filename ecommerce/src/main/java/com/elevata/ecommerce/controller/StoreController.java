package com.elevata.ecommerce.controller;

import com.elevata.ecommerce.dto.*;
import com.elevata.ecommerce.service.StoreService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/store")
public class StoreController {
    private final StoreService storeService;

    @Autowired
    public StoreController(StoreService storeService) {
        this.storeService = storeService;
    }

    @PostMapping
    public ResponseEntity<String> save(@Valid @RequestBody AddStoreDto dto) {
        String message = storeService.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(message);
    }

    @GetMapping("/page")
    public ResponseEntity<Page<StoreResponse>> getStoreListPage(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Page<StoreResponse> responses = storeService.getStoreListPage(page, size);
        return ResponseEntity.status(HttpStatus.FOUND).body(responses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StoreResponse> findById(@PathVariable int id) {
        StoreResponse response = storeService.findById(id);
        return ResponseEntity.status(HttpStatus.FOUND).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<StoreResponse> updateById(
            @PathVariable int id, @Valid @RequestBody UpdateStoreData data
    ) {
        StoreResponse response = storeService.updateById(id, data);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteById(@PathVariable int id) {
        String message = storeService.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).body(message);
    }
}
