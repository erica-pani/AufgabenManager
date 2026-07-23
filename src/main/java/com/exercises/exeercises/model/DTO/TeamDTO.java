package com.exercises.exeercises.model.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record TeamDTO(
    Long teamId,
    @NotBlank String name,
    @NotNull Long creatorId
) {}
