package com.exercises.exeercises.service;

import java.time.LocalDate;
import java.util.Collection;

import org.springframework.stereotype.Service;

import com.exercises.exeercises.model.Exercise;
import com.exercises.exeercises.model.ExerciseDTO;
import com.exercises.exeercises.model.Status;
import com.exercises.exeercises.repository.ExerciseRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ExerciseService {

    private final ExerciseRepository exerciseRepository;

    public ExerciseService(ExerciseRepository exerciseRepository) {
        this.exerciseRepository = exerciseRepository;
    }

    public Exercise saveNewExercise(ExerciseDTO exercise) {

        Exercise exerciseToBeSaved = new Exercise();
        exerciseToBeSaved.setTitle(exercise.getTitle());
        exerciseToBeSaved.setDescription(exercise.getDescription());
        exerciseToBeSaved.setStatus(Status.TODO);
        exerciseToBeSaved.setCreationDate(LocalDate.now());

        return exerciseRepository.save(exerciseToBeSaved);
    }

    public Collection<Exercise> getExercises() {

        return exerciseRepository.findAll();
    }

    public Exercise setExerciseToDone(Long exerciseId) {

        Exercise exercise = exerciseRepository
                .findById(exerciseId)
                .orElseThrow(() -> new EntityNotFoundException("Aufgabe nicht gefunden"));

        exercise.setStatus(Status.DONE);

        return exerciseRepository.save(exercise);
    }

    public Exercise setExerciseToInProgress(Long exerciseId) {
         Exercise exercise = exerciseRepository
                .findById(exerciseId)
                .orElseThrow(() -> new EntityNotFoundException("Aufgabe nicht gefunden"));

        exercise.setStatus(Status.IN_PROGRESS);

        return exerciseRepository.save(exercise);
    }

    public void deleteExercise(Long exerciseId) {

        if (!exerciseRepository.existsById(exerciseId)) {
            throw new EntityNotFoundException("Aufgabe nicht gefunden");
        }

       exerciseRepository.deleteById(exerciseId);
    }

    public Exercise editExercise(ExerciseDTO exerciseDTO, Long exerciseId) {

        Exercise exercise = exerciseRepository
                .findById(exerciseId)
                .orElseThrow(() -> new EntityNotFoundException("Aufgabe nicht gefunden"));
        
        exercise.setTitle(exerciseDTO.getTitle());
        exercise.setDescription(exerciseDTO.getDescription());
        
        return exerciseRepository.save(exercise);
        
    }


}