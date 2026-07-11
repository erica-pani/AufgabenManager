package com.exercises.exeercises.controller.mapper;

import org.mapstruct.Mapper;

import com.exercises.exeercises.model.dto.BoardResponseDTO;

@Mapper(componentModel = "spring")
public interface BoardMapper {

    BoardResponseDTO toDTO();
    
}
