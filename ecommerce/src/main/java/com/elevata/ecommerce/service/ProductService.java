package com.elevata.ecommerce.service;

import com.elevata.ecommerce.dto.AddProductDto;
import com.elevata.ecommerce.dto.ProductResponse;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public interface ProductService {

    String save(AddProductDto productDto);

    Page<ProductResponse> getProductList(int page, int size);
}
