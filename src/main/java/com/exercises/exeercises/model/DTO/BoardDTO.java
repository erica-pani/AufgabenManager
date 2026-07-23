package com.exercises.exeercises.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record BoardDTO(
    @NotBlank String name,
    boolean ownerisUser,
    @NotNull Long ownerId) {
} 
    
    


    
    

