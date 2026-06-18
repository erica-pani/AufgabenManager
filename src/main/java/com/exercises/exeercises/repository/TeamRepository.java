package com.exercises.exeercises.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.exercises.exeercises.model.Team;

public interface TeamRepository extends JpaRepository<Team, Long>{
    
}
