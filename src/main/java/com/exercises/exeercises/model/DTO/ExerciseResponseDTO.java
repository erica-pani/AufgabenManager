package com.exercises.exeercises.model.dto;

public record ExerciseResponseDTO(
    Integer exerciseNumber,
    Long boardId,
    String title, 
    String description,
    String status,
    String creationDate
) {}
