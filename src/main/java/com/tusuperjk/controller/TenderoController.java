package com.tusuperjk.controller;

import com.tusuperjk.model.User;
import com.tusuperjk.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/tendero")
public class TenderoController {

	@Autowired
	private UserService userService;

	@GetMapping("/dashboard")
	public String tenderoDashboard(Authentication authentication, Model model) {
		String username = authentication.getName();
		User user = userService.findByEmail(username)
				.orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

		model.addAttribute("username", user.getFirstName() + " " + user.getLastName());
		model.addAttribute("role", user.getRole());
		model.addAttribute("email", user.getEmail());

		// Redirigir a tu página existente de tendero
		return "tendero/dashboard"; // o el nombre de tu template existente
	}
}
