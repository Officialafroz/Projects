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

        return userRepository.findAll(pageable)
                .stream()
                .map(u -> new UserDto(u.getUserId(),
                        u.getName(),
                        u.getPhoneNumber(),
                        u.getPincode(),
                        u.getAddress())
                ).toList();
    }

    @Override
    public UserDto findById(int id) {

        User user = userRepository.findById(id)
                    .orElseThrow(() -> new UserNotFoundException("User not found for the id " + id));

        return new UserDto(user.getUserId(),
                user.getName(),
                user.getPhoneNumber(),
                user.getPincode(),
                user.getAddress());
    }
}
