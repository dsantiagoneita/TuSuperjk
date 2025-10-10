package com.tusuperjk.controller;

import com.tusuperjk.model.*;
import com.tusuperjk.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.transaction.annotation.Transactional;

@Controller
public class AuthController {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	private TenderoRepository tenderoRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	private static final String PASSWORD_PATTERN = "^(?=.*[A-Z]).{8,}$";
	private static final String EMAIL_PATTERN = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";

	@GetMapping("/login")
	public String login() {
		return "login";
	}

	@GetMapping("/register")
	public String showRegistrationForm(Model model) {
		model.addAttribute("user", new UserRegistrationDto());
		return "register";
	}

	@PostMapping("/register")
	@Transactional
	public String registerUser(@ModelAttribute("user") UserRegistrationDto userDto, BindingResult result, Model model) {

		// Validar email
		if (!isValidEmail(userDto.getEmail())) {
			result.rejectValue("email", "error.email", "❌ Formato de email inválido");
		}

		// Verificar si el email ya existe en User
		if (userRepository.existsByEmail(userDto.getEmail())) {
			result.rejectValue("email", "error.email", "❌ Este email ya está registrado");
		}

		// Validar contraseña
		if (!isValidPassword(userDto.getPassword())) {
			result.rejectValue("password", "error.password",
					"❌ La contraseña debe tener mínimo 8 caracteres y al menos una mayúscula");
		}

		// Verificar que las contraseñas coincidan
		if (!userDto.getPassword().equals(userDto.getConfirmPassword())) {
			result.rejectValue("confirmPassword", "error.confirmPassword", "❌ Las contraseñas no coinciden");
		}

		if (result.hasErrors()) {
			return "register";
		}

		// Crear y guardar usuario como CLIENTE por defecto
		User user = new User();
		user.setEmail(userDto.getEmail());
		user.setPassword(passwordEncoder.encode(userDto.getPassword()));
		user.setFirstName(userDto.getFirstName());
		user.setLastName(userDto.getLastName());
		user.setRole(Role.CLIENTE);
		user.setEnabled(true);

		User savedUser = userRepository.save(user);

		// Crear también el registro en Cliente para mantener compatibilidad
		Cliente cliente = new Cliente();
		cliente.setNombre(userDto.getFirstName() + " " + userDto.getLastName());
		cliente.setEmail(userDto.getEmail());
		clienteRepository.save(cliente);

		return "redirect:/login?success";
	}

	@GetMapping("/dashboard")
	public String dashboard(Authentication authentication, Model model) {
		// CORRECCIÓN: Usar getName() en lugar de name()
		String username = authentication.getName();

		// CORRECCIÓN: Cerrar el paréntesis y manejar el Optional correctamente
		User user = userRepository.findByEmail(username)
				.orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

		model.addAttribute("username", user.getFirstName());
		model.addAttribute("role", user.getRole());
		model.addAttribute("email", user.getEmail());

		// CORRECCIÓN: Redirigir según el rol (TENDERO con O, no con 0)
		switch (user.getRole()) {
		case ADMIN:
			return "redirect:/admin/dashboard";
		case TENDERO: // CORRECCIÓN: TENDERO con O, no con 0
			return "redirect:/tendero/dashboard";
		case CLIENTE:
			return "redirect:/cliente/dashboard";
		default:
			return "redirect:/home";
		}
	}

	private boolean isValidPassword(String password) {
		return password != null && password.matches(PASSWORD_PATTERN);
	}

	private boolean isValidEmail(String email) {
		return email != null && email.matches(EMAIL_PATTERN);
	}
}