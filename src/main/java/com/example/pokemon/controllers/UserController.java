package com.example.pokemon.controllers;

import com.example.pokemon.DTOs.UpdateUserRequest;
import com.example.pokemon.DTOs.UserResponse;
import com.example.pokemon.services.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/me")
    public ResponseEntity<UserResponse> getMe(@AuthenticationPrincipal OAuth2User principal) {
        if (principal == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return ResponseEntity.ok(userService.getOrCreateFromOAuth(principal));
    }

    @PutMapping("/me")
    public ResponseEntity<UserResponse> updateMe(
            @AuthenticationPrincipal OAuth2User principal,
            @Valid @RequestBody UpdateUserRequest request
    ) {
        if (principal == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return ResponseEntity.ok(userService.updateMyName(principal, request));
    }

    @DeleteMapping("/me")
    public ResponseEntity<Void> deleteMe(@AuthenticationPrincipal OAuth2User principal) {
        if (principal == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        userService.deleteMe(principal);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/me/favourites")
    public ResponseEntity<List<Long>> getMyFavourites(@AuthenticationPrincipal OAuth2User principal) {
        if (principal == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return ResponseEntity.ok(userService.getMyFavouritePokemonIds(principal));
    }

    @PostMapping("/me/favourites/{pokemonId}")
    public ResponseEntity<Void> addMyFavourite(
            @AuthenticationPrincipal OAuth2User principal,
            @PathVariable Long pokemonId
    ) {
        if (principal == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        userService.addMyFavouritePokemon(principal, pokemonId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/me/favourites/{pokemonId}")
    public ResponseEntity<Void> removeMyFavourite(
            @AuthenticationPrincipal OAuth2User principal,
            @PathVariable Long pokemonId
    ) {
        if (principal == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        userService.removeMyFavouritePokemon(principal, pokemonId);
        return ResponseEntity.noContent().build();
    }
}