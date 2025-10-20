package com.tusuperjk.controller;

import com.tusuperjk.model.Producto;
import com.tusuperjk.model.User;
import com.tusuperjk.repository.ProductoRepository;
import com.tusuperjk.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/tendero/inventario")
public class InventarioController {

	@Autowired
	private ProductoRepository productoRepository;

	@Autowired
	private UserRepository userRepository;

	@GetMapping("")
	public String mostrarInventario(Authentication authentication, Model model) {
		String username = authentication.getName();
		User user = userRepository.findByEmail(username)
				.orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

		List<Producto> productos = productoRepository.findAll();

		model.addAttribute("username", user.getFirstName() + " " + user.getLastName());
		model.addAttribute("productos", productos);
		model.addAttribute("productoNuevo", new Producto());

		return "inventario_tendero";
	}

	@PostMapping("/agregar")
	public String agregarProducto(@ModelAttribute Producto productoNuevo, Authentication authentication,
			RedirectAttributes redirectAttributes) {
		try {
			productoRepository.save(productoNuevo);
			redirectAttributes.addFlashAttribute("mensaje", "✅ Producto agregado exitosamente");
			redirectAttributes.addFlashAttribute("tipoMensaje", "success");
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("mensaje", "❌ Error al agregar producto");
			redirectAttributes.addFlashAttribute("tipoMensaje", "error");
		}

		return "redirect:/tendero/inventario";
	}

	@GetMapping("/editar/{id}")
	public String mostrarEditarProducto(@PathVariable Long id, Authentication authentication, Model model) {
		String username = authentication.getName();
		User user = userRepository.findByEmail(username)
				.orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

		Optional<Producto> producto = productoRepository.findById(id);

		if (producto.isPresent()) {
			model.addAttribute("username", user.getFirstName() + " " + user.getLastName());
			model.addAttribute("producto", producto.get());
			return "editar_producto";
		} else {
			return "redirect:/tendero/inventario";
		}
	}

	@PostMapping("/actualizar")
	public String actualizarProducto(@ModelAttribute Producto producto, Authentication authentication,
			RedirectAttributes redirectAttributes) {
		try {
			productoRepository.save(producto);
			redirectAttributes.addFlashAttribute("mensaje", "✅ Producto actualizado exitosamente");
			redirectAttributes.addFlashAttribute("tipoMensaje", "success");
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("mensaje", "❌ Error al actualizar producto");
			redirectAttributes.addFlashAttribute("tipoMensaje", "error");
		}

		return "redirect:/tendero/inventario";
	}

	@GetMapping("/eliminar/{id}")
	public String eliminarProducto(@PathVariable Long id, Authentication authentication,
			RedirectAttributes redirectAttributes) {
		try {
			productoRepository.deleteById(id);
			redirectAttributes.addFlashAttribute("mensaje", "✅ Producto eliminado exitosamente");
			redirectAttributes.addFlashAttribute("tipoMensaje", "success");
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("mensaje", "❌ Error al eliminar producto");
			redirectAttributes.addFlashAttribute("tipoMensaje", "error");
		}

		return "redirect:/tendero/inventario";
	}
}