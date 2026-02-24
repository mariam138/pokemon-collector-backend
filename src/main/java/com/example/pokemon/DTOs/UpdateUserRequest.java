package com.example.pokemon.DTOs;

import jakarta.validation.constraints.*;

public class UpdateUserRequest {

    @NotBlank(message = "Name is required")
    @Size(min = 3, max = 100, message =
            "Name must be between 3 and 100 char long")
    private String name;

    public UpdateUserRequest() {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
