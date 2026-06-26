package com.exercises.exeercises.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.exercises.exeercises.model.Exercise;
import com.exercises.exeercises.model.dto.ExerciseDTO;
import com.exercises.exeercises.model.id.ExerciseId;

public class ExerciseControllerTests {

    @BeforeEach
    public void setUp() {
    }

    @Test
    public void exerciseAddTest() {
        ExerciseDTO exerciseDTO = new ExerciseDTO();
        exerciseDTO.setBoardId(348L);
        exerciseDTO.setDescription("Beschreibung");
        exerciseDTO.setTitle("Titel");

        Exercise exercise = new Exercise();
        exercise.setId(new ExerciseId(348L, 1));;
        exercise.setTitle("Titel");
        exercise.setDescription("Beschreibung");
    }
}
