package com.exercises.exeercises.service;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.exercises.exeercises.model.Team;
import com.exercises.exeercises.model.User;
import com.exercises.exeercises.model.dto.TeamDTO;
import com.exercises.exeercises.repository.TeamRepository;
import com.exercises.exeercises.repository.UserRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class TeamService {

    private final TeamRepository teamRepository;
    private final UserRepository userRepository;

    public TeamService(TeamRepository teamRepository, UserRepository userRepository) {
        this.teamRepository = teamRepository;
        this.userRepository = userRepository;
    }

    public Team saveNewTeam(TeamDTO teamDTO) {
    
        Team team = new Team();
        team.setName(teamDTO.name());

        User creator = userRepository.findById(teamDTO.creatorId())
            .orElseThrow(() -> new EntityNotFoundException("Die Id vom Creator passt zu keinem User"));

        team.addMember(creator);

        return teamRepository.save(team);
    }

    public void addTeamMember() {

       
    }

    public Team changeTeamName(Long teamId, String newName) {

        Team team = teamRepository.findById(teamId)
            .orElseThrow(() -> new EntityNotFoundException());

        team.setName(newName);

        return teamRepository.save(team);
    }

    public void deleteTeam(Long teamId){
        
        if (!teamRepository.existsById(teamId)) {
            return;
        }

        teamRepository.deleteById(teamId);
    }

    public Collection<TeamDTO> myTeams(long userId) {

        if (!userRepository.existsById(userId)) {
            throw new IllegalArgumentException("User existiert nicht. Teams können nicht gefunden werden");
        }

        Set<Team> teams = userRepository.findById(userId).get().getTeams();
        Set<TeamDTO> teamdtos = new HashSet<>();

        for (Team team : teams) {
            teamdtos.add(new TeamDTO(team.getId(), team.getName(), null));
        }

        return teamdtos;
    }

}
