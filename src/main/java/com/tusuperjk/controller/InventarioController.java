package com.tusuperjk.controller;

import com.tusuperjk.model.Producto;
import com.tusuperjk.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/tendero/inventario") // todas las rutas de inventario quedan bajo /tendero/inventario
public class InventarioController {

	@Autowired
	private ProductoService productoService;

	// Mostrar lista de productos en inventario
	@GetMapping
	public String mostrarInventario(Model model) {
		model.addAttribute("productos", productoService.listarProductos());
		model.addAttribute("productoNuevo", new Producto()); // para el form de agregar
		return "tendero/inventario"; // tu HTML en templates
	}

	// Agregar producto al inventario
	@PostMapping("/agregar")
	public String agregarProducto(Producto producto) {
		productoService.guardarProducto(producto);
		return "redirect:/tendero/inventario"; // recarga lista después de guardar
	}

	// Editar producto (cargar formulario)
	@GetMapping("/editar/{id}")
	public String editarProducto(@PathVariable Long id, Model model) {
		Producto producto = productoService.obtenerProductoPorId(id)
				.orElseThrow(() -> new IllegalArgumentException("ID inválido: " + id));
		model.addAttribute("producto", producto);
		return "tendero/editar-producto"; // vista nueva
	}

	// Actualizar producto (guardar cambios)
	@PostMapping("/actualizar")
	public String actualizarProducto(Producto producto) {
		productoService.guardarProducto(producto); // save hace update si ya existe
		return "redirect:/tendero/inventario";
	}

	// Eliminar producto
	@GetMapping("/eliminar/{id}")
	public String eliminarProducto(@PathVariable Long id) {
		productoService.eliminarProducto(id);
		return "redirect:/tendero/inventario";
	}
}
