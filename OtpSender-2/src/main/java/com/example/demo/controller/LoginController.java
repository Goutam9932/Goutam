package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.model.User;
import com.example.demo.repo.UserRepository;

@Controller
public class LoginController {

	@Autowired
    private UserRepository userRepository;
	
    @GetMapping("/login")
    public String loginPage(Model model) {
        return "login"; // Return the login HTML page
    }
   


    @GetMapping("/admin/dashboard")
    public String dashboard() {
        return "admin_dashboard"; // Return the dashboard HTML page after login
    }
   
    @GetMapping("/user1/dashboard")
    public String dashboard1(Authentication authentication, Model model) {
        // Get the email of the currently logged-in user
        String email = authentication.getName();
        
        // Load user details using the email
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        
        // Add user object to model to be accessible in the view
        model.addAttribute("user", user);
        
        return "userdash"; // Return the dashboard HTML page for users
    }
}


