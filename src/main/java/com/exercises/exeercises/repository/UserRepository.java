package com.exercises.exeercises.repository;

import org.springframework.stereotype.Repository;

import com.exercises.exeercises.model.User;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;


@Repository
public interface UserRepository extends JpaRepository<User, Long>{
    
    Optional<User> findByUsername(String username);

    boolean existsByUsername(String username);
}
