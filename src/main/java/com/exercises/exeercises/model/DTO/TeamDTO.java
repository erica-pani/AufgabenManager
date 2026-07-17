package com.exercises.exeercises.model.dto;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record TeamDTO(
    @NotBlank String name,
    @NotNull Long creatorId
) {}
