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
import com.exercises.exeercises.model.dto.ExerciseDTO;
import com.exercises.exeercises.model.id.ExerciseId;
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

        return null;
    }

    @PutMapping("done/{boardId}/{exerciseNumber}")
    public ResponseEntity<Exercise> setExerciseToDone(@PathVariable Long boardId, @PathVariable Integer exerciseNumber) {
        
        Exercise exercise = exerciseService.setExerciseToDone(new ExerciseId(boardId, exerciseNumber));
        return new ResponseEntity<>(exercise, HttpStatus.OK);
    }

    @PutMapping("inProgress/{boardId}/{exerciseNumber}")
    public ResponseEntity<Exercise> setExerciseToInProgress(@PathVariable Long boardId, @PathVariable Integer exerciseNumber) {
        
        Exercise exercise = exerciseService.setExerciseToInProgress(new ExerciseId(boardId, exerciseNumber));
        return new ResponseEntity<>(exercise, HttpStatus.OK);
    }

    @PutMapping("/edit")
    public ResponseEntity<Exercise> editExercise(@RequestBody ExerciseDTO exerciseDTO, @RequestParam Long boardId, @RequestParam Integer exerciseNumber) {

        Exercise exercise = exerciseService.editExercise(exerciseDTO, new ExerciseId(boardId, exerciseNumber));
        return new ResponseEntity<>(exercise, HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/delete/{boardId}/{exerciseNumber}")
    public ResponseEntity<?> deleteExercise(@PathVariable Long boardId, @PathVariable Integer exerciseNumber) {

        exerciseService.deleteExercise(new ExerciseId(boardId, exerciseNumber));
        return ResponseEntity.status(HttpStatus.ACCEPTED).body("Aufgabe wurde gelöscht");
    }
    
}
