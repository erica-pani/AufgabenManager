package com.exercises.exeercises.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UserDTO (
    @Size(min = 4)
    @NotBlank 
    String username,

    @Size(min = 6) 
    @NotBlank 
    @Pattern(
        regexp = "^\\S+$"
    )
    String password) {}
