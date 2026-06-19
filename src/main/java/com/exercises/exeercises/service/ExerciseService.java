package com.exercises.exeercises.service;

import java.time.LocalDate;
import java.util.Collection;

import org.springframework.stereotype.Service;

import com.exercises.exeercises.model.Board;
import com.exercises.exeercises.model.Exercise;
import com.exercises.exeercises.model.dto.ExerciseDTO;
import com.exercises.exeercises.model.enums.Status;
import com.exercises.exeercises.model.id.ExerciseId;
import com.exercises.exeercises.repository.BoardRepository;
import com.exercises.exeercises.repository.ExerciseRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ExerciseService {

    private final ExerciseRepository exerciseRepository;
    private final BoardRepository boardRepository;

    public ExerciseService(ExerciseRepository exerciseRepository, BoardRepository boardRepository) {
        this.exerciseRepository = exerciseRepository;
        this.boardRepository = boardRepository;
    }

    /**
     * Die Methode nimmt ein DTO entgegen in dem Informationen zur einer neuen Aufgabe gespeichert sind. Erstellt ein neues Hausaufgaben Objekt,
     * mit den Parametern vom DTO und speichert es in der Datenbank
     * @param exercise DTO das die Attribute für die neue Aufgabe enthält
     * @return Die gespeicherte Entität
     */
    public Exercise saveNewExercise(ExerciseDTO exercise) {

        Integer maxExerciseNumber = exerciseRepository
            .findMaxExerciseNumber(exercise.getBoardId())
            .orElse(0);

        Board board = boardRepository
            .findById(exercise.getBoardId()).
            orElseThrow(() -> new EntityNotFoundException("board not found"));
            
        ExerciseId id = new ExerciseId(exercise.getBoardId(), maxExerciseNumber + 1);


        Exercise exerciseToBeSaved = new Exercise();
        exerciseToBeSaved.setId(id);
        exerciseToBeSaved.setBoard(board);
        exerciseToBeSaved.setTitle(exercise.getTitle());
        exerciseToBeSaved.setDescription(exercise.getDescription());
        exerciseToBeSaved.setStatus(Status.TODO);
        exerciseToBeSaved.setCreationDate(LocalDate.now());

        return exerciseRepository.save(exerciseToBeSaved);
    }

    public Collection<Exercise> getExercisesByBoardId(Long boardId) {

        return exerciseRepository.findAllByBoardId(boardId);
    }

    /**
     * Setzt den Status einer Übung auf {@link Status#DONE} und speichert die Änderung
     * in der Datenbank.
     *
     * @param exerciseId die ID der Übung, die als erledigt markiert werden soll
     * @return die aktualisierte und gespeicherte Übung
     * @throws EntityNotFoundException wenn keine Übung mit der angegebenen ID gefunden wird
     */
    public Exercise setExerciseToDone(ExerciseId exerciseId) {

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
    public Exercise setExerciseToInProgress(ExerciseId exerciseId) {
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
    public void deleteExercise(ExerciseId exerciseId) {

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
    public Exercise editExercise(ExerciseDTO exerciseDTO, ExerciseId exerciseId) {

        Exercise exercise = exerciseRepository
                .findById(exerciseId)
                .orElseThrow(() -> new EntityNotFoundException("Aufgabe nicht gefunden"));
        
        exercise.setTitle(exerciseDTO.getTitle());
        exercise.setDescription(exerciseDTO.getDescription());
        
        return exerciseRepository.save(exercise);
        
    }
}