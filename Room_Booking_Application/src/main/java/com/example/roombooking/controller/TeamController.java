package com.example.roombooking.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.roombooking.service.TeamService;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/teams")
public class TeamController {

	@Autowired
	private TeamService teamService;

	@PostMapping("/register")
	public String registerTeam(@RequestParam String username, @RequestParam String password) {
		// The service will return a message if the username already exists
		String registrationResult = teamService.registerTeam(username, password);
		return registrationResult;
	}

	@PostMapping("/login")
	public Map<String, Object> login(@RequestBody Map<String, String> loginRequest) {
		String username = loginRequest.get("username");
		String password = loginRequest.get("password");

		boolean isValidUser = teamService.validateTeam(username, password);
		Map<String, Object> response = new HashMap<>();

		if (isValidUser) {
			Long teamId = teamService.getTeamIdByUsername(username);
			response.put("success", true);
			response.put("teamId", teamId); // Send team ID for further use in the frontend
		} else {
			response.put("success", false);
			response.put("message", "Invalid username or password.");
		}

		return response;
	}
}