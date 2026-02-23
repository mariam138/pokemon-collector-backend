package com.example.pokemon.DTOs;

import jakarta.validation.constraints.*;

public class UpdateUserRequest {

    @Size(min = 3, max = 100, message =
            "Name must be between 3 and 100 char long")
    private String name;

    @Email(message = "Email must be valid")
    private String email;

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
}
