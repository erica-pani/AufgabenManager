package com.exercises.exeercises.model.dto;

import com.exercises.exeercises.model.enums.Owner;

public class BoardDTO {
    
    private String name;
    private boolean ownerisUser;
    private Long ownerId;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public boolean isOwnerisUser() {
        return ownerisUser;
    }

    public void setOwnerisUser(boolean ownerisUser) {
        this.ownerisUser = ownerisUser;
    }
    
    
}
