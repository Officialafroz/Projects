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

//    @GetMapping("/users")
//    public List<AppUser> getUsersList() {
//        return userService.getUsersList();
//    }
//
//    @GetMapping("/{userId}")
//    public AppUser getUser(@PathVariable int userId) {
//        return userService.findById(userId);
//    }
//
//    @DeleteMapping("/{userId}")
//    public String deleteUser(@PathVariable int userId) {
//        AppUser appUser = userService.findById(userId);
//
//        if (appUser == null) {
//            throw new RuntimeException("User not found for id");
//        }
//
//        userService.deleteById(userId);
//        return String.format("User for id %d is deleted.", userId);
//    }

    @PostMapping
    public ResponseEntity<String> save(@RequestBody UserCreationDTO userCreationDTO) {
        return ResponseEntity.ok(userService.save(userCreationDTO));
    }
}
