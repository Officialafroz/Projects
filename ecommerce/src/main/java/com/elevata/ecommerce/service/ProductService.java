package com.elevata.ecommerce.service;

import com.elevata.ecommerce.dto.AddProductDto;
import com.elevata.ecommerce.dto.ProductResponse;
import com.elevata.ecommerce.dto.UpdateProductDto;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public interface ProductService {

    String save(AddProductDto productDto);

    Page<ProductResponse> getProductListPage(int page, int size);

    ProductResponse findById(int id);

    String deleteById(int id);

    ProductResponse updateById(int id, UpdateProductDto dto);
}
