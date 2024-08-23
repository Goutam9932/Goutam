package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Student;
import com.example.demo.repo.Studentemp;
import com.example.demo.service.EmailService;

@RestController
public class EmpController {

    @Autowired
    private Studentemp studentemp;

    @Autowired
    private EmailService emailService;

    private static final int RECORD_LIMIT = 10;
    private static final String ADMIN_EMAIL = "goutamsatpathy69@gmail.com"; // Replace with actual admin email

   

    @PostMapping("/add")
    public Student createUser(@RequestBody Student s) {
        Student savedStudent = studentemp.save(s);
        if (studentemp.count() >= RECORD_LIMIT) {
            emailService.sendLimitReachedNotification(ADMIN_EMAIL);
        }
        return savedStudent;
    }
}
