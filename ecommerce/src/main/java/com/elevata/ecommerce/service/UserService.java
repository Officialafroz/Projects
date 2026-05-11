package com.elevata.ecommerce.service;

import com.elevata.ecommerce.dto.UpdateUserDto;
import com.elevata.ecommerce.dto.UserResponse;
import com.elevata.ecommerce.dto.UserRegistrationDto;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    String save(UserRegistrationDto dto);

    Page<UserResponse> getUsersListPage(int pageNo, int pageSize);

    UserResponse findById(int id);

    String deleteById(int id);

    UserResponse updateById(int id, UpdateUserDto dto);
}
