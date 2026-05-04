package com.elevata.ecommerce.controller;

import com.elevata.ecommerce.dto.AddStoreDto;
import com.elevata.ecommerce.dto.StoreResponse;
import com.elevata.ecommerce.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/store")
public class StoreController {
    private StoreService storeService;

    @Autowired
    public StoreController(StoreService storeService) {
        this.storeService = storeService;
    }

    @GetMapping("/page")
    public ResponseEntity<Page<StoreResponse>> getStoreListPage(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Page<StoreResponse> storePage = storeService.getStoreListPage(page, size);
        return new ResponseEntity<>(storePage, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<String> save(@Validated @RequestBody AddStoreDto addStoreDto) {
        return new ResponseEntity<>(storeService.save(addStoreDto), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StoreResponse> findById(@PathVariable int id) {
        StoreResponse storeResponse = storeService.findById(id);
        return new ResponseEntity<>(storeResponse, HttpStatus.FOUND);
    }
}
