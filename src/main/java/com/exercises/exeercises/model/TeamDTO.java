package com.exercises.exeercises.model;

import java.util.List;

public class TeamDTO {
    
    private String name;
    private List<Long> memberIds;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Long> getMemberIds() {
        return memberIds;
    }
    
    public void setMemberIds(List<Long> memberIds) {
        this.memberIds = memberIds;
    }

}
