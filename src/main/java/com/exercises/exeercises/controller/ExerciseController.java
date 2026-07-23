package com.exercises.exeercises.controller;

import java.util.Collection;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.exercises.exeercises.controller.mapper.ExerciseMapper;
import com.exercises.exeercises.model.Exercise;
import com.exercises.exeercises.model.dto.ExerciseDTO;
import com.exercises.exeercises.model.dto.ExerciseResponseDTO;
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

    private final ExerciseMapper exerciseMapper;

    ExerciseController(ExerciseService exerciseService, ExerciseMapper exerciseMapper) {
        this.exerciseService = exerciseService;
        this.exerciseMapper = exerciseMapper;
    }

    @PostMapping(value = "/add",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<ExerciseResponseDTO> newExercise(@RequestBody ExerciseDTO exerciseDTO) {

        Exercise exercise = exerciseService.saveNewExercise(exerciseDTO);
        return new ResponseEntity<>(exerciseMapper.toDto(exercise), HttpStatus.CREATED);
    }

    @GetMapping("{boardId}")
    public ResponseEntity<Collection<Exercise>> getExercises(@PathVariable Long boardId) {

        Collection<Exercise> exercises = exerciseService.getExercisesByBoardId(boardId);
        return new ResponseEntity<>(exercises, HttpStatus.ACCEPTED);
    }

    @PutMapping("/done/{boardId}/{exerciseNumber}")
    public ResponseEntity<ExerciseResponseDTO> setExerciseToDone(@PathVariable Long boardId, @PathVariable Integer exerciseNumber) {
        
        Exercise exercise = exerciseService.setExerciseToDone(new ExerciseId(boardId, exerciseNumber));
        return new ResponseEntity<>(exerciseMapper.toDto(exercise), HttpStatus.OK);
    }

    @PutMapping("/inProgress/{boardId}/{exerciseNumber}")
    public ResponseEntity<ExerciseResponseDTO> setExerciseToInProgress(@PathVariable Long boardId, @PathVariable Integer exerciseNumber) {
        
        Exercise exercise = exerciseService.setExerciseToInProgress(new ExerciseId(boardId, exerciseNumber));
        return new ResponseEntity<>(exerciseMapper.toDto(exercise), HttpStatus.OK);
    }

    @PutMapping("/edit")
    public ResponseEntity<ExerciseResponseDTO> editExercise(@RequestBody ExerciseDTO exerciseDTO, @RequestParam Long boardId, @RequestParam Integer exerciseNumber) {

        Exercise exercise = exerciseService.editExercise(exerciseDTO, new ExerciseId(boardId, exerciseNumber));
        return new ResponseEntity<>(exerciseMapper.toDto(exercise), HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/delete/{boardId}/{exerciseNumber}")
    public ResponseEntity<?> deleteExercise(@PathVariable Long boardId, @PathVariable Integer exerciseNumber) {

        exerciseService.deleteExercise(new ExerciseId(boardId, exerciseNumber));
        return ResponseEntity.status(HttpStatus.ACCEPTED).body("Aufgabe wurde geloescht");
    }
    
}
