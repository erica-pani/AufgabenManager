package com.exercises.exeercises.model.dto;

import java.util.List;

public record TeamDTO(
    String name,
    List<Long> memberIds
) {
    
    

}
