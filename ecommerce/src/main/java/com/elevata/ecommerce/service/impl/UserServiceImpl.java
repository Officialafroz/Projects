package com.elevata.ecommerce.service.impl;

import com.elevata.ecommerce.dto.UpdateUserDto;
import com.elevata.ecommerce.dto.UserDto;
import com.elevata.ecommerce.dto.UserRegistrationDto;
import com.elevata.ecommerce.entity.User;
import com.elevata.ecommerce.exception.ResourceNotFoundException;
import com.elevata.ecommerce.exception.UserNotFoundException;
import com.elevata.ecommerce.repository.UserRepository;
import com.elevata.ecommerce.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

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
                dto.getName(), dto.getPhoneNumber(), Integer.parseInt(dto.getPincode()), dto.getAddress())
        );

        return "User saved to DB.";
    }

    @Override
    public Page<UserDto> getUsers(int pageNo, int pageSize) {

        Pageable pageable = PageRequest.of(pageNo, pageSize);

        //Required error handling for no users
        return userRepository.findAll(pageable)
                .map(this::makeDto);
    }

    @Override
    public UserDto findById(int id) {
        User user = fetchUser(id);
        return makeDto(user);
    }

    @Override
    public String deleteById(int id) {
        User user = fetchUser(id);
        userRepository.delete(user);

        return "User deleted for id " + id;
    }

    @Override
    public UserDto updateById(int id, UpdateUserDto dto) {
        User user = fetchUser(id);

        //Without 'dto' it will throw request body is missing, hence the condition won't work
        if (dto != null) {
            Optional.ofNullable(dto.getPhoneNumber())
                    .ifPresent(user::setPhoneNumber);

            if (dto.getPostalCode() != null) {
                Integer parsedCode = Integer.parseInt(dto.getPostalCode());

                Optional.ofNullable(parsedCode)
                        .ifPresent(user::setPincode);
            }

            Optional.ofNullable(dto.getAddress())
                    .ifPresent(user::setAddress);

            user = userRepository.save(user);
        } else {
            throw new ResourceNotFoundException("Data is empty for update operation for id " + id);
        }

        return makeDto(user);
    }

    private UserDto makeDto(User user) {
        return UserDto.builder()
                .userId(user.getUserId())
                .name(user.getName())
                .phoneNumber(user.getPhoneNumber())
                .pincode(user.getPincode())
                .address(user.getAddress())
                .build();
    }

    private User fetchUser(int id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found for id " + id));
    }
}
