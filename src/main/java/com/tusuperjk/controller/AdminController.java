package com.tusuperjk.controller;

import com.tusuperjk.model.User;
import com.tusuperjk.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/dashboard")
    public String adminDashboard(Authentication authentication, Model model) {
        String username = authentication.getName();
        User user = userRepository.findByEmail(username)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        
        model.addAttribute("username", user.getFirstName() + " " + user.getLastName());
        model.addAttribute("role", user.getRole());
        model.addAttribute("email", user.getEmail());
        
        return "admin/dashboard";
    }

    @GetMapping("/users")
    public String manageUsers(Authentication authentication, Model model) {
        String username = authentication.getName();
        User user = userRepository.findByEmail(username)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        
        model.addAttribute("username", user.getFirstName());
        model.addAttribute("users", userRepository.findAll());
        
        return "admin/users";
    }
}