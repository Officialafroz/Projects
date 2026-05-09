package com.elevata.ecommerce.controller;

import com.elevata.ecommerce.dto.UpdateUserDto;
import com.elevata.ecommerce.dto.UserDto;
import com.elevata.ecommerce.dto.UserRegistrationDto;
import com.elevata.ecommerce.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {
    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<String> save(@Valid @RequestBody UserRegistrationDto dto) {
        return ResponseEntity.ok(userService.save(dto));
    }

    @GetMapping("/page")
    public ResponseEntity<Page<UserDto>> getUsers(
            @RequestParam(defaultValue = "0") int pageNo,
            @RequestParam(defaultValue = "10") int pageSize
    ) {
        return ResponseEntity.ok(userService.getUsers(pageNo, pageSize));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> findById(@PathVariable int id) {
        UserDto user = userService.findById(id);
        return ResponseEntity.ok(user);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateById(
            @PathVariable int id, @Valid @RequestBody UpdateUserDto updateUserDto) {
        UserDto user = userService.updateById(id, updateUserDto);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteById(@PathVariable int id) {
        return new ResponseEntity<>(userService.deleteById(id), HttpStatus.OK);
    }
}
