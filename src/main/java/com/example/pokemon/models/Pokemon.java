package com.example.pokemon.models;

import jakarta.persistence.*;

@Entity (name = "Pokemons")
public class Pokemon {

    @Id
    private Long id;

    public Pokemon() {}

    public Pokemon(Long id) {
        this.id = id;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

}