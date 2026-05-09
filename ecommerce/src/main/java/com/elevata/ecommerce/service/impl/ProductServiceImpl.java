package com.elevata.ecommerce.service.impl;

import com.elevata.ecommerce.dto.AddProductDto;
import com.elevata.ecommerce.dto.ProductResponse;
import com.elevata.ecommerce.dto.UpdateProductDto;
import com.elevata.ecommerce.entity.Product;
import com.elevata.ecommerce.exception.ProductNotFoundException;
import com.elevata.ecommerce.exception.ResourceNotFoundException;
import com.elevata.ecommerce.repository.ProductRepository;
import com.elevata.ecommerce.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

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

    @Override
    public ProductResponse findById(int id) {
        Product product = fetchProduct(id);
        return makeDto(product);
    }

    @Override
    public String deleteById(int id) {
        Product product = fetchProduct(id);
        productRepository.delete(product);

        return "Product deleted for id " + id;
    }

    @Override
    public ProductResponse updateById(int id, UpdateProductDto dto) {
        Product product = fetchProduct(id);

        //Without 'dto' it will throw request body is missing, hence the condition won't work
        if (dto != null) {
            if (dto.getStock() != null) {
                product.setStock(product.getStock() + dto.getStock());
            }

            Optional.ofNullable(dto.getPrice())
                    .ifPresent(product::setPrice);

            product = productRepository.save(product);
        } else {
            throw new ResourceNotFoundException("Data is empty for update operation for id " + id);
        }

        return makeDto(product);
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

    private Product fetchProduct(int id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found for id " + id));
    }
}
