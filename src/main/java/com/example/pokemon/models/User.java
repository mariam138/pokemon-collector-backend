package com.example.pokemon.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @NotBlank
    @Column(name = "name")
    private String name;

    @NotBlank
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @JsonIgnore
    @ManyToMany
    @JoinTable(
            name = "user_favourite_pokemons",
            joinColumns = @JoinColumn(
                    name = "user_id",
                    referencedColumnName = "user_id"
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "pokemon_id",
                    referencedColumnName = "pokemon_id"
            )
    )

    private List<Pokemon> favouritePokemons = new ArrayList<>();

    public User() {
    }

    public Long getId() {
        return id;
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

    public List<Pokemon> getFavouritePokemons() {
        return favouritePokemons;
    }

    public void setFavouritePokemons(List<Pokemon> favouritePokemons) {
        this.favouritePokemons = favouritePokemons;
    }
}