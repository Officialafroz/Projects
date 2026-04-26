package com.elevata.gsrtc.controller;

import com.elevata.gsrtc.dto.UserCreationDTO;
import com.elevata.gsrtc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/end-user")
public class AppUserController {
    private final UserService userService;

    @Autowired
    public AppUserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<String> save(@RequestBody UserCreationDTO userCreationDTO) {
        return ResponseEntity.ok(userService.save(userCreationDTO));
    }
}
