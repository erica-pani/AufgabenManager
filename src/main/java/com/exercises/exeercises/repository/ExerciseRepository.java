package com.exercises.exeercises.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.exercises.exeercises.model.Exercise;

@Repository
public interface ExerciseRepository extends JpaRepository<Exercise, Long>{
    
    boolean existsById(Long id);
}   
