package com.tusuperjk;

import com.tusuperjk.model.Cliente;
import com.tusuperjk.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TusuperjkApplication implements CommandLineRunner {

	@Autowired
	private ClienteRepository clienteRepository;

	public static void main(String[] args) {
		SpringApplication.run(TusuperjkApplication.class, args);
	}

	@Override
	public void run(String... args) {

		clienteRepository.findByEmail("neita@email.com")
				.ifPresentOrElse(c -> System.out.println("Ya existe el cliente con email: " + c.getEmail()), () -> {
					Cliente cliente = new Cliente("Neita", "neita@email.com");
					clienteRepository.save(cliente);
					System.out.println("Cliente insertado: " + cliente.getNombre());
				});
	}
}
