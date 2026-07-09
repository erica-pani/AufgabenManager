package com.exercises.exeercises.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.exercises.exeercises.model.Board;

public interface BoardRepository extends JpaRepository<Board, Long>{
    
    @Query("""
        SELECT b
        FROM Board b
        WHERE b.owner = 'USER'
        AND b.ownerId = :userId
        """)
    List<Board> findPrivateBoardsByOwnerId(Long userId);

    @Query("""
        SELECT b
        FROM Board b
        WHERE b.owner = 'TEAM'
        AND b.ownerId = :teamId
        """)
    List<Board> findTeamBoardsByOwnerId(Long teamId);
}
