package com.exercises.exeercises.controller.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.exercises.exeercises.model.Board;
import com.exercises.exeercises.model.dto.BoardResponseDTO;

@Mapper(componentModel = "spring")
public interface BoardMapper {

    @Mapping(source = "id", target = "boardId")
    //@Mapping(source = "", target = "")
    BoardResponseDTO toDTO(Board board);
    
}
