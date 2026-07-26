package com.exercises.exeercises.config;

import org.springframework.stereotype.Component;

import com.exercises.exeercises.repository.TeamRepository;

@Component("userSecurity")
public class UserSecurity {
    
    private final TeamRepository teamRepository;

    public UserSecurity(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    public boolean isTeamMember() {
        return true;
    }

    public boolean isTeamAdmin() {
        return false;
    }

    public boolean isSelf() {
        return true;
    }
}
