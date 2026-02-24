package com.example.pokemon.models;


import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity(name = "Users")
public class User {
    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private String email;

    private String password;

    @ManyToMany
    @JoinTable(
            name = "user_favourite_pokemons",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "pokemon_id")
    )
    private List<Pokemon> favouritePokemons = new ArrayList<>();

    public User() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Pokemon> getFavouritePokemons() {
        return favouritePokemons;
    }

    public void setFavouritePokemons(List<Pokemon> favouritePokemons) {
        this.favouritePokemons = favouritePokemons;
    }
}