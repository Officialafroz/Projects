package com.elevata.gsrtc.service;

import com.elevata.gsrtc.dto.UserCreationDTO;
import com.elevata.gsrtc.entity.AppUser;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
    List<AppUser> getUsersList();

    String save(UserCreationDTO userCreationDTO);

    void deleteById(long userId);

    AppUser findById(long userId);

    AppUser findUserByEmail(String email);
}
