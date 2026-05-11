package com.elevata.ecommerce.service;

import com.elevata.ecommerce.dto.AddSellerDto;
import com.elevata.ecommerce.dto.SellerResponse;
import com.elevata.ecommerce.dto.UpdateSeller;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public interface SellerService {

    String save(AddSellerDto dto);

    Page<SellerResponse> getSellers(int page, int size);

    SellerResponse findById(int id);

    SellerResponse updateById(int id, UpdateSeller dto);

    String deleteById(int id);
}
