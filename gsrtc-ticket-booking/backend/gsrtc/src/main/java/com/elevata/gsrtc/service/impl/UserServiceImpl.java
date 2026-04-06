package com.elevata.gsrtc.service.impl;

import com.elevata.gsrtc.dto.UserCreationDTO;
import com.elevata.gsrtc.entity.AppUser;
import com.elevata.gsrtc.repository.AppUserRepository;
import com.elevata.gsrtc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final AppUserRepository appUserRepository;

    @Autowired
    public UserServiceImpl(AppUserRepository appUserRepository) {
        this.appUserRepository = appUserRepository;
    }

    @Override
    public List<AppUser> getUsersList() {
        List<AppUser> appUsers = appUserRepository.findAll();

        if (appUsers != null) {
            return appUsers;
        }

        throw new RuntimeException("There are no users in the database.");
    }

    @Transactional
    @Override
    public String save(UserCreationDTO userCreationDTO) {
        AppUser appUser = new AppUser();
        appUser.setName(userCreationDTO.getName());
        appUser.setEmail(userCreationDTO.getEmail());
        appUser.setPhoneNumber(userCreationDTO.getPhoneNumber());
        appUser.setGender(userCreationDTO.getGender());

        appUserRepository.save(appUser);
        return "Account has been created successfully.";
    }

    @Transactional
    @Override
    public void deleteById(long userId) {
        appUserRepository.deleteById(userId);
    }

    @Transactional
    @Override
    public AppUser findById(long userId) {
        Optional<AppUser> user = appUserRepository.findById(userId);

        if (user.isPresent()) {
            return user.get();
        } else {
            throw new RuntimeException("User not found for id");
        }
    }

    @Transactional
    @Override
    public AppUser findUserByEmail(String email) {
        return appUserRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
}
