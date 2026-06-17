package com.exercises.exeercises.service;

import java.time.LocalDate;
import java.util.Collection;

import org.springframework.stereotype.Service;

import com.exercises.exeercises.model.Exercise;
import com.exercises.exeercises.model.Status;
import com.exercises.exeercises.model.dto.ExerciseDTO;
import com.exercises.exeercises.repository.ExerciseRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ExerciseService {

    private final ExerciseRepository exerciseRepository;

    public ExerciseService(ExerciseRepository exerciseRepository) {
        this.exerciseRepository = exerciseRepository;
    }

    /**
     * Die Methode nimmt ein DTO entgegen in dem Informationen zur einer neuen Aufgabe gespeichert sind. Erstellt ein neues Hausaufgaben Objekt,
     * mit den Parametern vom DTO und speichert es in der Datenbank
     * @param exercise DTO das die Attribute für die neue Aufgabe enthält
     * @return Die gespeicherte Entität
     */
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

    /**
     * Setzt den Status einer Übung auf {@link Status#DONE} und speichert die Änderung
     * in der Datenbank.
     *
     * @param exerciseId die ID der Übung, die als erledigt markiert werden soll
     * @return die aktualisierte und gespeicherte Übung
     * @throws EntityNotFoundException wenn keine Übung mit der angegebenen ID gefunden wird
     */
    public Exercise setExerciseToDone(Long exerciseId) {

        Exercise exercise = exerciseRepository
                .findById(exerciseId)
                .orElseThrow(() -> new EntityNotFoundException("Aufgabe nicht gefunden"));

        exercise.setStatus(Status.DONE);

        return exerciseRepository.save(exercise);
    }

    /**
     * Setzt den Status einer Übung auf {@link Status#IN_PROGRESS} und speichert die Änderung
     * in der Datenbank.
     *
     * @param exerciseId die ID der Übung, die als in Bearbeitung markiert werden soll
     * @return die aktualisierte und gespeicherte Übung
     * @throws EntityNotFoundException wenn keine Übung mit der angegebenen ID gefunden wird
     */
    public Exercise setExerciseToInProgress(Long exerciseId) {
         Exercise exercise = exerciseRepository
                .findById(exerciseId)
                .orElseThrow(() -> new EntityNotFoundException("Aufgabe nicht gefunden"));

        exercise.setStatus(Status.IN_PROGRESS);

        return exerciseRepository.save(exercise);
    }

    /**
     * Löscht eine Übung anhand ihrer ID.
     *
     * @param exerciseId die ID der zu löschenden Übung
     * @throws EntityNotFoundException wenn keine Übung mit der angegebenen ID gefunden wird
     */
    public void deleteExercise(Long exerciseId) {

        if (!exerciseRepository.existsById(exerciseId)) {
            throw new EntityNotFoundException("Aufgabe nicht gefunden");
        }

       exerciseRepository.deleteById(exerciseId);
    }

    /**
     * Aktualisiert die Daten einer bestehenden Übung anhand der übergebenen Informationen
     * und speichert die Änderungen in der Datenbank.
     *
     * @param exerciseDTO die neuen Daten der Übung
     * @param exerciseId die ID der zu bearbeitenden Übung
     * @return die aktualisierte und gespeicherte Übung
     * @throws EntityNotFoundException wenn keine Übung mit der angegebenen ID gefunden wird
     */
    public Exercise editExercise(ExerciseDTO exerciseDTO, Long exerciseId) {

        Exercise exercise = exerciseRepository
                .findById(exerciseId)
                .orElseThrow(() -> new EntityNotFoundException("Aufgabe nicht gefunden"));
        
        exercise.setTitle(exerciseDTO.getTitle());
        exercise.setDescription(exerciseDTO.getDescription());
        
        return exerciseRepository.save(exercise);
        
    }
}