package com.example.pokemon.controllers;

import com.example.pokemon.DTOs.UserResponse;
import com.example.pokemon.services.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    public UserController(UserService userService) {
        this.userService = userService;
    };

    @GetMapping
    public List<UserResponse> getAllUsers() {
        return userService.findAllUsers();
    }







}


