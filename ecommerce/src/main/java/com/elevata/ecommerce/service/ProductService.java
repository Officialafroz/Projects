package com.elevata.ecommerce.service;

import com.elevata.ecommerce.dto.AddProductDto;
import com.elevata.ecommerce.dto.ProductResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProductService {

    String save(AddProductDto productDto);

    List<ProductResponse> getProductList(int page, int size);
}
