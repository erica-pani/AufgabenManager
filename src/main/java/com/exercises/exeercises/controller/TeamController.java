package com.exercises.exeercises.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.exercises.exeercises.model.Team;
import com.exercises.exeercises.model.dto.TeamDTO;
import com.exercises.exeercises.service.TeamService;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequestMapping("/team")
public class TeamController {

    private final TeamService teamService;

    TeamController(TeamService teamService) {
        this.teamService = teamService;
    }
    
    @PostMapping("/create")
    public ResponseEntity<TeamDTO> createTeam(@RequestBody TeamDTO teamDTO) {
        Team team = teamService.saveNewTeam(teamDTO);
        return new ResponseEntity<>(new TeamDTO(team.getName(),
            team.getMember().iterator().next().getId()), HttpStatus.CREATED);
    }

    @PutMapping("/edit")
    public ResponseEntity<?> changeName(@RequestParam Long teamId, @RequestParam String name) {
        
        Team team = teamService.changeTeamName(teamId, name);
        return ResponseEntity.ok("Das Team hat den neuen Namen: " + team.getName());
    }

    @DeleteMapping("/delete/{teamId}")
    public ResponseEntity<?> deleteMapping(@PathVariable Long teamId) {

        teamService.deleteTeam(teamId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
