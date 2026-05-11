package com.elevata.ecommerce.service.impl;

import com.elevata.ecommerce.dto.*;
import com.elevata.ecommerce.entity.Seller;
import com.elevata.ecommerce.exception.ResourceNotFoundException;
import com.elevata.ecommerce.exception.SellerNotFoundException;
import com.elevata.ecommerce.repository.SellerRepository;
import com.elevata.ecommerce.service.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class SellerServiceImpl implements SellerService {

    private final SellerRepository sellerRepository;

    @Autowired
    public SellerServiceImpl(SellerRepository sellerRepository) {
        this.sellerRepository = sellerRepository;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String save(AddSellerDto dto) {
        Seller seller = new Seller();
        seller.setFullName(dto.getFullName());
        seller.setAddress(dto.getAddress());
        seller.setEmail(dto.getEmail());
        seller.setPhoneNumber(dto.getPhoneNumber());

        sellerRepository.save(seller);

        return "Seller saved to DB.";
    }

    @Override
    public Page<SellerResponse> getSellers(int pageNo, int pageSize) {

        Pageable pageable = PageRequest.of(pageNo, pageSize);

        //Required error handling for no users
        return sellerRepository.findAll(pageable)
                .map(this::makeDto);
    }

    @Override
    public SellerResponse findById(int id) {
        Seller seller = fetchSeller(id);
        return makeDto(seller);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String deleteById(int id) {
        Seller seller = fetchSeller(id);
        sellerRepository.delete(seller);

        return "Seller deleted for id " + id;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public SellerResponse updateById(int id, UpdateSeller dto) {
        Seller seller = fetchSeller(id);

        //Without 'dto' it will throw request body is missing, hence the condition won't work
        if (dto != null) {
            Optional.ofNullable(dto.getPhoneNumber())
                    .ifPresent(seller::setPhoneNumber);

            Optional.ofNullable(dto.getEmail())
                    .ifPresent(seller::setEmail);

            Optional.ofNullable(dto.getAddress())
                    .ifPresent(seller::setAddress);

            seller = sellerRepository.save(seller);
        } else {
            throw new ResourceNotFoundException("Data is empty for update operation for id " + id);
        }

        return makeDto(seller);
    }

    private SellerResponse makeDto(Seller seller) {
        return SellerResponse.builder()
                .sellerId(seller.getSellerId())
                .fullName(seller.getFullName())
                .phoneNumber(seller.getPhoneNumber())
                .email(seller.getEmail())
                .address(seller.getAddress())
                .createdAt(seller.getCreatedAt())
                .updatedAt(seller.getUpdatedAt())
                .build();
    }

    private Seller fetchSeller(int id) {
        return sellerRepository.findById(id)
                .orElseThrow(() -> new SellerNotFoundException("Seller not found for id " + id));
    }
}
