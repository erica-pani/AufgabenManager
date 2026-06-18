package com.exercises.exeercises.model.dto;

import com.exercises.exeercises.model.enums.Owner;

public class BoardDTO {
    
    private String name;
    private Owner owner;
    private Long ownerId;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }
    
    
}
