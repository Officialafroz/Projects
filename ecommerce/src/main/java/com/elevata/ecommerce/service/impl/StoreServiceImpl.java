package com.elevata.ecommerce.service.impl;

import com.elevata.ecommerce.dto.AddStoreDto;
import com.elevata.ecommerce.dto.StoreResponse;
import com.elevata.ecommerce.dto.UserDto;
import com.elevata.ecommerce.entity.Store;
import com.elevata.ecommerce.entity.User;
import com.elevata.ecommerce.enums.Country;
import com.elevata.ecommerce.enums.State;
import com.elevata.ecommerce.exception.StoreNotFoundException;
import com.elevata.ecommerce.exception.UserNotFoundException;
import com.elevata.ecommerce.repository.StoreRepository;
import com.elevata.ecommerce.repository.UserRepository;
import com.elevata.ecommerce.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class StoreServiceImpl implements StoreService {
    private StoreRepository storeRepository;
    private UserRepository userRepository;

    @Autowired
    public StoreServiceImpl(StoreRepository storeRepository, UserRepository userRepository) {
        this.storeRepository = storeRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Page<StoreResponse> getStoreListPage(int page, int size) {

        Pageable pageable = PageRequest.of(page, size);

        return storeRepository.findAll(pageable)
                .map(this::fetchStoreResponse);
    }

    @Override
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

    private UserDto fetchUser(int id) {
        User vendor = fetchVendor(id);

            return UserDto.builder()
                    .userId(vendor.getUserId())
                    .name(vendor.getName())
                    .phoneNumber(vendor.getPhoneNumber())
                    .pincode(vendor.getPincode())
                    .address(vendor.getAddress())
                    .build();
    }

    private Store fetchStore(int id) {
        return storeRepository.findById(id)
                .orElseThrow(() -> new StoreNotFoundException("Store not found for id " + id));
    }

    private User fetchVendor(int id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found for id " + id));
    }

    private StoreResponse fetchStoreResponse(Store store) {

        return StoreResponse.builder()
                .storeId(store.getStoreId())
                .name(store.getName())
                .vendor(fetchUser(store.getVendor().getUserId()))
                .address(String.format("%s, %s, %s, %s",
                        store.getAddressLine(), store.getCity(), store.getState(), store.getCountry()))
                .contactNumber(store.getContactNumber())
                .postalCode(store.getPostalCode())
                .isActive(store.isActive())
                .build();
    }
}
