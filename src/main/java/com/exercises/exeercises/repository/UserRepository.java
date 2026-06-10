package com.exercises.exeercises.repository;

import org.springframework.stereotype.Repository;

import com.exercises.exeercises.model.User;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
    
}
