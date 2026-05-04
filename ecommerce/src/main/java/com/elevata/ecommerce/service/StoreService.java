package com.elevata.ecommerce.service;

import com.elevata.ecommerce.dto.AddStoreDto;
import com.elevata.ecommerce.dto.StoreResponse;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public interface StoreService {
    Page<StoreResponse> getStoreListPage(int page, int size);

    String save(AddStoreDto addStoreDto);

    StoreResponse findById(int id);
}
