package com.exercises.exeercises.controller.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.exercises.exeercises.model.Exercise;
import com.exercises.exeercises.model.dto.ExerciseResponseDTO;

@Mapper(componentModel = "spring")
public interface ExerciseMapper {
    
    @Mapping(source = "id.exerciseNumber", target = "exerciseNumber")
    @Mapping(source = "id.boardId", target = "boardId")
    ExerciseResponseDTO toDto(Exercise exercise);
}
