package com.elevata.ecommerce.controller;

import com.elevata.ecommerce.dto.*;
import com.elevata.ecommerce.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/product")
public class ProductController {
    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<String> save(@Valid @RequestBody AddProductDto dto) {
        String message = productService.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(message);
    }

    @GetMapping("/page")
    public ResponseEntity<Page<ProductResponse>> getProductListPage(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Page<ProductResponse> responses = productService.getProductListPage(page, size);
        return ResponseEntity.status(HttpStatus.FOUND).body(responses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> findById(@PathVariable int id) {
        ProductResponse productResponse = productService.findById(id);
        return ResponseEntity.status(HttpStatus.FOUND).body(productResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponse> updateById(
            @PathVariable int id, @Valid @RequestBody UpdateProductDto dto
    ) {
        ProductResponse productResponse = productService.updateById(id, dto);
        return ResponseEntity.status(HttpStatus.OK).body(productResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteById(@PathVariable int id) {
        String message = productService.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).body(message);
    }
}
