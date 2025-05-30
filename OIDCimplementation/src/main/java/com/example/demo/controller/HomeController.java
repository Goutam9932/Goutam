package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

@Controller
public class HomeController {

    @GetMapping("/home")
    public String home(@AuthenticationPrincipal OAuth2User oauthUser, Model model) {
        model.addAttribute("name", oauthUser.getAttribute("given_name"));
        model.addAttribute("email", oauthUser.getAttribute("email"));
        return "home";
    }

   
}
