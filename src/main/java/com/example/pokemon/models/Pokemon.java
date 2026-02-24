package com.example.pokemon.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "pokemons")
public class Pokemon {

    @Id
    @Positive
    @Column(name = "pokemon_id", nullable = false)
    private Long id;

    @NotBlank
    @Column(name = "pokemon_name", nullable = false)
    private String name;

    private String ability;

    private Long baseExperience;
    private Long height;
    private Long weight;
    private String typeOne;

    private String typeTwo;

    @JsonIgnore
    @ManyToMany(mappedBy = "favouritePokemons")
    private List<User> users = new ArrayList<>();

    public Pokemon() {}

    public Pokemon(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getAbility() { return ability; }
    public void setAbility(String ability) { this.ability = ability; }

    public Long getBaseExperience() { return baseExperience; }
    public void setBaseExperience(Long baseExperience) { this.baseExperience = baseExperience; }

    public Long getHeight() { return height; }
    public void setHeight(Long height) { this.height = height; }

    public Long getWeight() { return weight; }
    public void setWeight(Long weight) { this.weight = weight; }

    public String getTypeOne() { return typeOne; }
    public void setTypeOne(String typeOne) { this.typeOne = typeOne; }

    public String getTypeTwo() { return typeTwo; }
    public void setTypeTwo(String typeTwo) { this.typeTwo = typeTwo; }

    public List<User> getUsers() { return users; }
    public void setUsers(List<User> users) { this.users = users; }
}