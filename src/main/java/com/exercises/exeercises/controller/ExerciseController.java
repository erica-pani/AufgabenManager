package com.exercises.exeercises.controller;

import java.util.Collection;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.exercises.exeercises.model.Exercise;
import com.exercises.exeercises.model.ExerciseDTO;
import com.exercises.exeercises.service.ExerciseService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/exercise")
public class ExerciseController {
    
    private final ExerciseService exerciseService;

    ExerciseController(ExerciseService exerciseService) {
        this.exerciseService = exerciseService;
    }

    @PostMapping("/add")
    public ResponseEntity<Exercise> newExercise(@RequestBody ExerciseDTO exerciseDTO) {

        Exercise exercise = exerciseService.saveNewExercise(exerciseDTO);
        return new ResponseEntity<>(exercise, HttpStatus.CREATED);
    }

    @GetMapping("/myExercises")
    public ResponseEntity<Collection<Exercise>> getExercises() {

        Collection<Exercise> exercises = exerciseService.getExercises();
        return new ResponseEntity<>(exercises, HttpStatus.FOUND);
    }

    @PutMapping("done/{id}")
    public ResponseEntity<Exercise> setExerciseToDone(@PathVariable Long id) {
        
        Exercise exercise = exerciseService.setExerciseToDone(id);
        return new ResponseEntity<>(exercise, HttpStatus.OK);
    }

    @PutMapping("inProgress/{id}")
    public ResponseEntity<Exercise> setExerciseToInProgress(@PathVariable Long id) {
        
        Exercise exercise = exerciseService.setExerciseToInProgress(id);
        return new ResponseEntity<>(exercise, HttpStatus.OK);
    }

    @PutMapping("/edit")
    public ResponseEntity<Exercise> editExercise(@RequestBody ExerciseDTO exerciseDTO, @RequestParam Long id) {

        Exercise exercise = exerciseService.editExercise(exerciseDTO, id);
        return new ResponseEntity<>(exercise, HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/delete/{exerciseId}")
    public ResponseEntity<?> deleteExercise(@PathVariable Long exerciseId) {

        exerciseService.deleteExercise(exerciseId);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body("Aufgabe wurde gelöscht");
    }
    
}
