package com.example.pokemon.services;

import com.example.pokemon.DTOs.UserResponse;
import com.example.pokemon.models.User;
import com.example.pokemon.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepo;

    // Constructor injection
    public UserService(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    // Helper method to convert a User entity into a UserResponse DTO
    private UserResponse mapToResponse(User user) {
        return new UserResponse(user.getId(), user.getName(), user.getEmail());
    }

    // Retrieve all users
    public List<UserResponse> findAllUsers() {
        return userRepo.findAll().stream().map(this::mapToResponse).toList();
    }

    //READ on GET request
    public UserResponse findUserById(Long id) {
        return userRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("User with ID: %d, was not found", id)
                ));
    }















}