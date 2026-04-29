package com.elevata.ecommerce.service;

import com.elevata.ecommerce.dto.UserDto;
import com.elevata.ecommerce.dto.UserRegistrationDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
    String save(UserRegistrationDto dto);

    List<UserDto> getUsers(int pageNo, int pageSize);

    UserDto findById(int id);
}
