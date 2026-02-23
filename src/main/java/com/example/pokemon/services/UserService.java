package com.example.pokemon.services;

import com.example.pokemon.DTOs.CreateUserRequest;
import com.example.pokemon.DTOs.UpdateUserRequest;
import com.example.pokemon.DTOs.UserResponse;
import com.example.pokemon.models.User;
import com.example.pokemon.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import com.example.pokemon.models.Pokemon;
import com.example.pokemon.repositories.PokemonRepository;
//import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepo;
    private final PokemonRepository pokemonRepo;
//    private final PasswordEncoder passwordEncoder;

    // Constructor injection
//    public UserService(UserRepository userRepo, PasswordEncoder passwordEncoder) {
//        this.userRepo = userRepo;
//        this.passwordEncoder = passwordEncoder;
//    }

    public UserService(UserRepository userRepo, PokemonRepository pokemonRepo) {
        this.userRepo = userRepo;
        this.pokemonRepo = pokemonRepo;
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
        User user = userRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("User with ID: %d, was not found", id)
                ));

        return mapToResponse(user);
    }

    // CREATE ON POST Request
    public UserResponse addUser(CreateUserRequest newUserRequest) {
        if (userRepo.existsByEmail(newUserRequest.getEmail())) {
            throw new IllegalArgumentException(
                    "Email already in use: " + newUserRequest.getEmail()
            );
        }

        User user = new User();
        user.setName(newUserRequest.getName());
        user.setEmail(newUserRequest.getEmail());
//        user.setPassword(passwordEncoder.encode(newUserRequest.getPassword()));
        user.setPassword(newUserRequest.getPassword());

        User saved = userRepo.save(user);
        return mapToResponse(saved);
    }

    public UserResponse updateUser(Long id, UpdateUserRequest request) {
        if(request.getEmail() == null && request.getName() == null) {
            throw new IllegalArgumentException("Either email or name need to " +
                    "be updated");
        }

        User user =
                userRepo.findById(id).orElseThrow(() -> new EntityNotFoundException(String.format("User with ID: %d was not found", id)));
        if(!(request.getName() == null) && !request.getName().isBlank()) {
            user.setName(request.getName());
        }
        if(!(request.getEmail() == null) && !request.getEmail().isBlank()) {
            user.setEmail(request.getEmail());
        }
        User updated = userRepo.save(user);
        return mapToResponse(updated);
    }

    //    DELETE
    public void deleteUser(Long id) {
        if(!userRepo.existsById(id)) {
            throw new EntityNotFoundException(String.format(
                    "User with ID: %d, was not found", id));
        }
        userRepo.deleteById(id);
    }

}