package com.example.pokemon.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "Pokemons")
public class Pokemon {

    @Id
    @GeneratedValue
    private Long id;
    @Positive
    private Long pokemonId;
    private String pokemonName;
    private String ability;
    private Long baseExperience;
    private Long height;
    private Long weight;
    private String typeOne;
    private String typeTwo;


    @ManyToMany(mappedBy = "favouritePokemons")
    private List<User> users = new ArrayList<>();


    public Pokemon() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}