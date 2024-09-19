package com.example.demo.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Image;

public interface ImageRepository extends JpaRepository<Image, Long> {
}