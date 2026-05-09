package com.elevata.ecommerce.controller;

import com.elevata.ecommerce.dto.*;
import com.elevata.ecommerce.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/product")
public class ProductController {

    private ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<?> save(@Validated @RequestBody AddProductDto productDto) {
        return new ResponseEntity<>(productService.save(productDto), HttpStatus.CREATED);
    }

    @GetMapping("/page")
    public ResponseEntity<Page<ProductResponse>> getProductList(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return new ResponseEntity<>(productService.getProductList(page, size), HttpStatus.FOUND);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> findById(@PathVariable int id) {
        ProductResponse productResponse = productService.findById(id);
        return new ResponseEntity<>(productResponse, HttpStatus.FOUND);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponse> updateById(
            @PathVariable int id, @Validated @RequestBody UpdateProductDto productDto
    ) {
        ProductResponse productResponse = productService.updateById(id, productDto);
        return new ResponseEntity<>(productResponse, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteById(@PathVariable int id) {
        return new ResponseEntity<>(productService.deleteById(id), HttpStatus.OK);
    }
}
