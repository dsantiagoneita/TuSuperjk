package com.tusuperjk.controller;

import com.tusuperjk.model.Producto;
import com.tusuperjk.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/tendero/inventario") // todas las rutas de inventario quedan bajo /tendero/inventario
public class InventarioController {

	@Autowired
	private ProductoRepository productoRepository;

	// Mostrar lista de productos en inventario
	@GetMapping
	public String mostrarInventario(Model model) {
		model.addAttribute("productos", productoRepository.findAll());
		model.addAttribute("productoNuevo", new Producto()); // para el form de agregar
		return "inventario_tendero"; // tu HTML en templates
	}

	// Agregar producto al inventario
	@PostMapping("/agregar")
	public String agregarProducto(Producto producto) {
		productoRepository.save(producto);
		return "redirect:/tendero/inventario"; // recarga lista después de guardar
	}

	// Editar producto (cargar formulario)
	@GetMapping("/editar/{id}")
	public String editarProducto(@PathVariable Long id, Model model) {
		Producto producto = productoRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("ID inválido: " + id));
		model.addAttribute("producto", producto);
		return "editar_producto"; // vista nueva
	}

	// Actualizar producto (guardar cambios)
	@PostMapping("/actualizar")
	public String actualizarProducto(Producto producto) {
		productoRepository.save(producto); // save hace update si ya existe
		return "redirect:/tendero/inventario";
	}

	// Eliminar producto
	@GetMapping("/eliminar/{id}")
	public String eliminarProducto(@PathVariable Long id) {
		productoRepository.deleteById(id);
		return "redirect:/tendero/inventario";
	}
}
