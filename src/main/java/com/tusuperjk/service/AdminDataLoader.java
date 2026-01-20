package com.tusuperjk.service;

import com.tusuperjk.model.Role;
import com.tusuperjk.model.User;
import com.tusuperjk.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AdminDataLoader implements CommandLineRunner {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public void run(String... args) throws Exception {
		// Crear administradores principales
		createUserIfNotExists("Andrey@admin.com", "Admin123456", "Andrey", "Rondon", Role.ADMIN);
		createUserIfNotExists("neita@admin.com", "Admin123456", "David", "Neita", Role.ADMIN);

		// Usuarios de ejemplo
		createUserIfNotExists("tendero@ejemplo.com", "Tendero123456", "Maria", "Tienda", Role.TENDERO);
		createUserIfNotExists("cliente@ejemplo.com", "Cliente123456", "Carlos", "Cliente", Role.CLIENTE);
	}

	private void createUserIfNotExists(String email, String password, String firstName, String lastName, Role role) {
		if (userRepository.findByEmail(email).isEmpty()) {
			User user = new User();
			user.setEmail(email);
			user.setPassword(passwordEncoder.encode(password));
			user.setFirstName(firstName);
			user.setLastName(lastName);
			user.setRole(role);
			user.setEnabled(true);

			userRepository.save(user);
			System.out.println("Usuario creado: " + email + " como " + role);
		}
	}
}