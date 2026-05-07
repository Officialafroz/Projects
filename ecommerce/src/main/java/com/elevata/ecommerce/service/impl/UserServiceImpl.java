package com.elevata.ecommerce.service.impl;

import com.elevata.ecommerce.dto.UserDto;
import com.elevata.ecommerce.dto.UserRegistrationDto;
import com.elevata.ecommerce.entity.User;
import com.elevata.ecommerce.exception.UserNotFoundException;
import com.elevata.ecommerce.repository.UserRepository;
import com.elevata.ecommerce.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public String save(UserRegistrationDto dto) {

        userRepository.save(new User(
                dto.getName(), dto.getPhoneNumber(), dto.getPincode(), dto.getAddress())
        );

        return "User saved to DB.";
    }

    @Override
    public List<UserDto> getUsers(int pageNo, int pageSize) {

        Pageable pageable = PageRequest.of(pageNo, pageSize);

        //Required error handling for no users
        return userRepository.findAll(pageable)
                .stream()
                .map(user -> UserDto.builder()
                        .userId(user.getUserId())
                        .name(user.getName())
                        .phoneNumber(user.getPhoneNumber())
                        .pincode(user.getPincode())
                        .address(user.getAddress())
                        .build()
                ).toList();
    }

    @Override
    public UserDto findById(int id) {

        User user = userRepository.findById(id)
                    .orElseThrow(() -> new UserNotFoundException("User not found for the id " + id));

        return UserDto.builder()
                .userId(user.getUserId())
                .name(user.getName())
                .phoneNumber(user.getPhoneNumber())
                .pincode(user.getPincode())
                .address(user.getAddress())
                .build();
    }
}
