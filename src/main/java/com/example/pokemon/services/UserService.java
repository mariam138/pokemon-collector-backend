package com.example.pokemon.services;

import com.example.pokemon.DTOs.UpdateUserRequest;
import com.example.pokemon.DTOs.UserResponse;
import com.example.pokemon.models.Pokemon;
import com.example.pokemon.models.User;
import com.example.pokemon.repositories.PokemonRepository;
import com.example.pokemon.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepo;
    private final PokemonRepository pokemonRepo;

    public UserService(UserRepository userRepo, PokemonRepository pokemonRepo) {
        this.userRepo = userRepo;
        this.pokemonRepo = pokemonRepo;
    }

    private UserResponse mapToResponse(User user) {
        return new UserResponse(user.getId(), user.getName(), user.getEmail());
    }

    private User getMe(OAuth2User principal) {
        if (principal == null) {
            throw new IllegalStateException("Not authenticated.");
        }

        String email = principal.getAttribute("email");
        if (email == null || email.isBlank()) {
            throw new IllegalStateException("OAuth login did not provide an email address.");
        }

        return userRepo.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("User not found for email: " + email));
    }

    public UserResponse getOrCreateFromOAuth(OAuth2User principal) {
        if (principal == null) {
            throw new IllegalStateException("Not authenticated.");
        }

        String email = principal.getAttribute("email");
        String name = principal.getAttribute("name");

        if (email == null || email.isBlank()) {
            throw new IllegalStateException("OAuth login did not provide an email address.");
        }

        User user = userRepo.findByEmail(email).orElseGet(() -> {
            User newUser = new User();
            newUser.setEmail(email);
            newUser.setName((name != null && !name.isBlank()) ? name : "Unknown");
            return userRepo.save(newUser);
        });

        return mapToResponse(user);
    }

    public UserResponse updateMyName(OAuth2User principal, UpdateUserRequest request) {
        if (request == null || request.getName() == null || request.getName().isBlank()) {
            throw new IllegalArgumentException("Name must not be blank.");
        }

        User user = getMe(principal);
        user.setName(request.getName().trim());
        return mapToResponse(userRepo.save(user));
    }

    public void deleteMe(OAuth2User principal) {
        User user = getMe(principal);
        userRepo.delete(user);
    }

    public List<Long> getMyFavouritePokemonIds(OAuth2User principal) {
        User user = getMe(principal);
        return user.getFavouritePokemons()
                .stream()
                .map(Pokemon::getId)
                .toList();
    }

    public void addMyFavouritePokemon(OAuth2User principal, Long pokemonId) {
        if (pokemonId == null || pokemonId <= 0) {
            throw new IllegalArgumentException("pokemonId must be positive.");
        }

        User user = getMe(principal);

        Pokemon pokemon = pokemonRepo.findById(pokemonId)
                .orElseThrow(() -> new EntityNotFoundException("Pokemon not found: " + pokemonId));

        boolean alreadyFavourite = user.getFavouritePokemons()
                .stream()
                .anyMatch(p -> p.getId().equals(pokemonId));

        if (!alreadyFavourite) {
            user.getFavouritePokemons().add(pokemon);
            userRepo.save(user);
        }
    }

    public void removeMyFavouritePokemon(OAuth2User principal, Long pokemonId) {
        if (pokemonId == null || pokemonId <= 0) {
            throw new IllegalArgumentException("pokemonId must be positive.");
        }

        User user = getMe(principal);

        boolean removed = user.getFavouritePokemons()
                .removeIf(p -> p.getId().equals(pokemonId));

        if (removed) {
            userRepo.save(user);
        }
    }
}