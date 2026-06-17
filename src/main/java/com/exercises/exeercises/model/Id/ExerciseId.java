package com.exercises.exeercises.model.id;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Embeddable;

@Embeddable
public class ExerciseId implements Serializable{
    
    private Long boardId;
    private Integer exerciseNumber;

    public ExerciseId() {}

    public ExerciseId(Long boardId, Integer exerciseNumber) {
        this.boardId = boardId;
        this.exerciseNumber = exerciseNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ExerciseId)) return false;
        ExerciseId that = (ExerciseId) o;
        return Objects.equals(boardId, that.boardId)
                && Objects.equals(exerciseNumber, that.exerciseNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(boardId, exerciseNumber);
    }

    public Long getBoardId() {
        return boardId;
    }

    public void setBoardId(Long boardId) {
        this.boardId = boardId;
    }

    public Integer getExerciseNumber() {
        return exerciseNumber;
    }

    public void setExerciseNumber(Integer exerciseNumber) {
        this.exerciseNumber = exerciseNumber;
    }

    

}
