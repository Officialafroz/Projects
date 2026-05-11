package com.elevata.ecommerce.service.impl;

import com.elevata.ecommerce.dto.*;
import com.elevata.ecommerce.entity.Seller;
import com.elevata.ecommerce.entity.Store;
import com.elevata.ecommerce.entity.User;
import com.elevata.ecommerce.enums.Country;
import com.elevata.ecommerce.enums.State;
import com.elevata.ecommerce.exception.ResourceNotFoundException;
import com.elevata.ecommerce.exception.SellerNotFoundException;
import com.elevata.ecommerce.exception.StoreNotFoundException;
import com.elevata.ecommerce.exception.UserNotFoundException;
import com.elevata.ecommerce.repository.SellerRepository;
import com.elevata.ecommerce.repository.StoreRepository;
import com.elevata.ecommerce.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class StoreServiceImpl implements StoreService {
    private StoreRepository storeRepository;
    private SellerRepository sellerRepository;

    @Autowired
    public StoreServiceImpl(StoreRepository storeRepository, SellerRepository sellerRepository) {
        this.storeRepository = storeRepository;
        this.sellerRepository = sellerRepository;
    }

    @Override
    public Page<StoreResponse> getStoreListPage(int page, int size) {

        Pageable pageable = PageRequest.of(page, size);

        return storeRepository.findAll(pageable)
                .map(this::fetchStoreResponse);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String save(AddStoreDto addStoreDto) {
        Store store = new Store();
        store.setName(addStoreDto.getName());
        store.setAddressLine(addStoreDto.getAddressLine());
        store.setCity(addStoreDto.getCity());
        store.setState(State.fromString(addStoreDto.getState()));

        //Currently the services are available in India only.
        store.setCountry(Country.INDIA);
        store.setPostalCode(Integer.parseInt(addStoreDto.getPostalCode()));
        store.setContactNumber(addStoreDto.getContactNumber());
        store.setActive(false);
        store.setVendor(fetchVendor(addStoreDto.getVendorId()));

        storeRepository.save(store);

        return "Store created successfully.";
    }

    @Override
    public StoreResponse findById(int id) {
        Store store = fetchStore(id);
        return fetchStoreResponse(store);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String deleteById(int id) {
        Store store = fetchStore(id);
        storeRepository.delete(store);

        return "Store deleted for id " + id;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public StoreResponse updateById(int id, UpdateStoreData data) {
        Store store = fetchStore(id);

        //Without 'data' it will throw request body is missing, hence the condition won't work
        if (data != null) {
            Optional.ofNullable(data.getAddressLine())
                    .ifPresent(store::setAddressLine);

            Optional.ofNullable(data.getCity())
                    .ifPresent(store::setCity);

            Optional.ofNullable(data.getContactNumber())
                    .ifPresent(store::setContactNumber);

            if (data.getPostalCode() != null) {
                Integer parsedCode = Integer.parseInt(data.getPostalCode());

                Optional.ofNullable(parsedCode)
                        .ifPresent(store::setPostalCode);
            }

            store = storeRepository.save(store);
        } else {
            throw new ResourceNotFoundException("Data is empty for update operation for id " + id);
        }

        return fetchStoreResponse(store);
    }

    private SellerResponse fetchSeller(int id) {
        Seller vendor = fetchVendor(id);

        return SellerResponse.builder()
                .sellerId(vendor.getSellerId())
                .fullName(vendor.getFullName())
                .phoneNumber(vendor.getPhoneNumber())
                .email(vendor.getEmail())
                .address(vendor.getAddress())
                .build();
    }

    private Store fetchStore(int id) {
        return storeRepository.findById(id)
                .orElseThrow(() -> new StoreNotFoundException("Store not found for id " + id));
    }

    private Seller fetchVendor(int id) {
        return sellerRepository.findById(id)
                .orElseThrow(() -> new SellerNotFoundException("Seller not found for id " + id));
    }

    private StoreResponse fetchStoreResponse(Store store) {

        return StoreResponse.builder()
                .storeId(store.getStoreId())
                .name(store.getName())
                .vendor(fetchSeller(store.getVendor().getSellerId()))
                .address(String.format("%s, %s, %s, %s",
                        store.getAddressLine(), store.getCity(), store.getState(), store.getCountry()))
                .contactNumber(store.getContactNumber())
                .postalCode(store.getPostalCode())
                .isActive(store.isActive())
                .build();
    }
}

