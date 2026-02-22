package com.example.pokemon.services;

import com.example.pokemon.DTOs.UserResponse;
import com.example.pokemon.models.User;
import com.example.pokemon.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepo;

    public UserService(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    public List<UserResponse> findAllUsers() {
        return userRepo.findAll().stream().map(this::mapToResponse).toList();
    }

    private UserResponse mapToResponse(User user) {
        return new UserResponse(user.getId(), user.getName(), user.getEmail());
    }

}