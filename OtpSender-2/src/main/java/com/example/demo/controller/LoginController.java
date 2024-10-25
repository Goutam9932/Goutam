package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.model.User;
import com.example.demo.repo.UserRepository;

@Controller
public class LoginController {

	
	
    @GetMapping("/login")
    public String loginPage(Model model) {
        return "login"; // Return the login HTML page
    }

    @GetMapping("/admin/dashboard")
    public String dashboard() {
        return "admin_dashboard"; // Return the dashboard HTML page after login
    }
   
}
