package com.elevata.ecommerce.service.impl;

import com.elevata.ecommerce.dto.AddProductDto;
import com.elevata.ecommerce.dto.ProductResponse;
import com.elevata.ecommerce.entity.Product;
import com.elevata.ecommerce.repository.ProductRepository;
import com.elevata.ecommerce.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {
    private ProductRepository productRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public String save(AddProductDto productDto) {
        Product product = new Product();
        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setStock(productDto.getStock());
        product.setPrice(productDto.getPrice());

        productRepository.save(product);

        return "Product added to Database.";
    }

    @Override
    public Page<ProductResponse> getProductList(int page, int size) {

        Pageable pageable = PageRequest.of(page, size);

        //Required error handling for 0 products
        return productRepository.findAll(pageable)
                .map(this::makeDto);
    }

    private ProductResponse makeDto(Product product) {
        return ProductResponse.builder()
                .productId(product.getProductId())
                .name(product.getName())
                .description(product.getDescription())
                .stock(product.getStock())
                .price(product.getPrice())
                .build();
    }
}
