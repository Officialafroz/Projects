package com.elevata.ecommerce.service;

import com.elevata.ecommerce.dto.UserDto;
import com.elevata.ecommerce.dto.UserRegistrationDto;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    String save(UserRegistrationDto dto);

    Page<UserDto> getUsers(int pageNo, int pageSize);

    UserDto findById(int id);
}
