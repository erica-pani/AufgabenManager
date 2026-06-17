package com.exercises.exeercises.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.exercises.exeercises.model.Exercise;
import com.exercises.exeercises.model.id.ExerciseId;

public interface ExerciseRepository extends JpaRepository<Exercise, ExerciseId>{
    
    boolean existsById(ExerciseId id);

    @Query("""
        SELECT MAX(e.id.exerciseNumber)
        FROM Exercise e
        WHERE e.id.boardId = :boardId
        """)
    Optional<Integer> findMaxExerciseNumber(Long boardId);

}   
