package com.elevata.ecommerce.controller;

import com.elevata.ecommerce.dto.UpdateUserDto;
import com.elevata.ecommerce.dto.UserResponse;
import com.elevata.ecommerce.dto.UserRegistrationDto;
import com.elevata.ecommerce.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<String> save(@Valid @RequestBody UserRegistrationDto dto) {
        String message = userService.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(message);
    }

    @GetMapping("/page")
    public ResponseEntity<Page<UserResponse>> getUsersListPage(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Page<UserResponse> responses = userService.getUsersListPage(page, size);
        return ResponseEntity.status(HttpStatus.FOUND).body(responses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> findById(@PathVariable int id) {
        UserResponse response = userService.findById(id);
        return ResponseEntity.status(HttpStatus.FOUND).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> updateById(
            @PathVariable int id, @Valid @RequestBody UpdateUserDto updateUserDto
    ) {
        UserResponse response = userService.updateById(id, updateUserDto);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteById(@PathVariable int id) {
        String message = userService.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).body(message);
    }
}
