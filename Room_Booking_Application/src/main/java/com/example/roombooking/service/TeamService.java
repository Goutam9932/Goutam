package com.example.roombooking.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.roombooking.model.Team;
import com.example.roombooking.repository.TeamRepository;

@Service
public class TeamService {

    @Autowired
    private TeamRepository teamRepository;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    // Register team with encrypted password
    public String registerTeam(String username, String password) {
        // Check if the username already exists
        if (teamRepository.findByUsername(username) != null) {
            return "Username already exists!";
        }
        
        Team team = new Team();
        team.setUsername(username);
        team.setPassword(passwordEncoder.encode(password)); // Encrypt password before saving
        teamRepository.save(team);

        return "Team registered successfully!";
    }

    // Validate team credentials
    public boolean validateTeam(String username, String password) {
        Team team = teamRepository.findByUsername(username);
        if (team != null) {
            return passwordEncoder.matches(password, team.getPassword());
        }
        return false;
    }

    public Long getTeamIdByUsername(String username) {
        Team team = teamRepository.findByUsername(username);
        return team != null ? team.getId() : null;
    }
}