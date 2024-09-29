package com.example.roombooking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.roombooking.model.Team;

@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {
    // Check if the username already exists
    boolean existsByUsername(String username);

    // Find team by username
    Team findByUsername(String username);
}