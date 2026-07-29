package com.exercises.exeercises.controller;

import java.util.Collection;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.exercises.exeercises.model.Team;
import com.exercises.exeercises.model.User;
import com.exercises.exeercises.model.UserPrincipal;
import com.exercises.exeercises.model.dto.TeamDTO;
import com.exercises.exeercises.service.TeamService;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;



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
        return new ResponseEntity<>(new TeamDTO(
            team.getId(), 
            team.getName(),
            team.getMember().iterator().next().getId()), HttpStatus.CREATED);
    }

    @PutMapping("/edit")
    //@PreAuthorize("@userSecurity.isTeamAdmin(#teamId, authentication.principal.id)")
    public ResponseEntity<?> changeName(@RequestParam Long teamId, @RequestParam String name) {
        
        Team team = teamService.changeTeamName(teamId, name);
        return ResponseEntity.ok("Das Team hat den neuen Namen: " + team.getName());
    }

    @DeleteMapping("/delete/{teamId}")
    //@PreAuthorize("@userSecurity.isTeamAdmin(#teamId, authentication.principal.id)")
    public ResponseEntity<?> deleteMapping(@PathVariable Long teamId) {

        teamService.deleteTeam(teamId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/teams")
    public ResponseEntity<Collection<TeamDTO>> myTeams(@AuthenticationPrincipal UserPrincipal userdetails) {
        Collection<TeamDTO> teams = teamService.myTeams(userdetails.getId());
        return new ResponseEntity<>(teams, HttpStatus.ACCEPTED);
    }
    
    @GetMapping("/member")
    //@PreAuthorize("@userSecurity.isTeamMember(#teamId)")
    public ResponseEntity<?> teamMember(@RequestParam(name = "tId", required = true) Long teamId) {
        Collection<User> member = teamService.getTeamMember(teamId);
        return new ResponseEntity<>(member, HttpStatus.OK);
    }
}
