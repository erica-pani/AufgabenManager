package com.exercises.exeercises.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.exercises.exeercises.model.Board;

public interface BoardRepository extends JpaRepository<Board, Long>{
    
}
