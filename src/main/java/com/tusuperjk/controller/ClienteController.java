package com.tusuperjk.controller;

import com.tusuperjk.model.Cliente;
import com.tusuperjk.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

	@Autowired
	private ClienteRepository clienteRepository;

	//  GET: listar todos
	@GetMapping
	public List<Cliente> getAllClientes() {
		return clienteRepository.findAll();
	}

	//  POST: crear cliente con validación de email duplicado
	@PostMapping
	public ResponseEntity<?> createCliente(@RequestBody Cliente cliente) {
		if (clienteRepository.findByEmail(cliente.getEmail()).isPresent()) {
			return ResponseEntity.badRequest().body("❌ El email ya está registrado: " + cliente.getEmail());
		}
		Cliente nuevoCliente = clienteRepository.save(cliente);
		return ResponseEntity.ok(nuevoCliente);
	}

	// PUT: actualizar cliente por ID
	@PutMapping("/{id}")
	public ResponseEntity<?> updateCliente(@PathVariable Long id, @RequestBody Cliente clienteDetails) {
		Optional<Cliente> clienteExistente = clienteRepository.findById(id);

		if (clienteExistente.isEmpty()) {
			return ResponseEntity.notFound().build();
		}

		Cliente cliente = clienteExistente.get();
		cliente.setNombre(clienteDetails.getNombre());

		// Si se intenta cambiar el email, validar duplicado
		if (!cliente.getEmail().equals(clienteDetails.getEmail())) {
			if (clienteRepository.findByEmail(clienteDetails.getEmail()).isPresent()) {
				return ResponseEntity.badRequest().body("❌ El email ya está en uso.");
			}
			cliente.setEmail(clienteDetails.getEmail());
		}

		Cliente actualizado = clienteRepository.save(cliente);
		return ResponseEntity.ok(actualizado);
	}

	// DELETE: eliminar cliente por ID
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteCliente(@PathVariable Long id) {
		if (!clienteRepository.existsById(id)) {
			return ResponseEntity.notFound().build();
		}
		clienteRepository.deleteById(id);
		return ResponseEntity.ok("✅ Cliente eliminado con éxito.");
	}
}
