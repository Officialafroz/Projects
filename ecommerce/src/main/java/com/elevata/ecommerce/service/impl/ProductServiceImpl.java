package com.elevata.ecommerce.service.impl;

import com.elevata.ecommerce.dto.AddProductDto;
import com.elevata.ecommerce.dto.ProductResponse;
import com.elevata.ecommerce.entity.Product;
import com.elevata.ecommerce.repository.ProductRepository;
import com.elevata.ecommerce.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    private ProductRepository productRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }


    @Override
    public String save(AddProductDto productDto) {
        productRepository.save(new Product(
                productDto.getName(),
                productDto.getDescription(),
                productDto.getStock(),
                productDto.getPrice()
        ));

        return "Product added to DB.";
    }

    @Override
    public List<ProductResponse> getProductList(int page, int size) {

        Pageable pageable = PageRequest.of(page, size);

        return productRepository.findAll(pageable)
                .stream()
                .map(product -> new ProductResponse(
                        product.getProductId(),
                        product.getName(),
                        product.getDescription(),
                        product.getStock(),
                        product.getPrice()
                )).toList();
    }
}
