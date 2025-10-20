package com.tusuperjk.controller;

import com.tusuperjk.model.User;
import com.tusuperjk.model.Pedido;
import com.tusuperjk.repository.UserRepository;
import com.tusuperjk.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/tendero")
public class TenderoController {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PedidoRepository pedidoRepository;

	@GetMapping("/homepage")
	public String tenderoHomepage(Authentication authentication, Model model) {
		String username = authentication.getName();
		User user = userRepository.findByEmail(username)
				.orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

		model.addAttribute("username", user.getFirstName() + " " + user.getLastName());
		model.addAttribute("role", user.getRole());
		model.addAttribute("email", user.getEmail());

		return "HomepageTendero";
	}

	@GetMapping("/dashboard")
	public String tenderoDashboard(Authentication authentication, Model model) {
		return tenderoHomepage(authentication, model);
	}

	@GetMapping("/pedidos")
	public String pedidos(Authentication authentication, Model model) {
		String username = authentication.getName();
		User user = userRepository.findByEmail(username)
				.orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

		// Obtener pedidos por estado
		List<Pedido> pedidosPendientes = pedidoRepository.findByEstado("PENDIENTE");
		List<Pedido> pedidosEnProceso = pedidoRepository.findByEstado("EN_PROCESO");
		List<Pedido> pedidosCompletados = pedidoRepository.findByEstado("COMPLETADO");

		model.addAttribute("username", user.getFirstName() + " " + user.getLastName());
		model.addAttribute("pedidosPendientes", pedidosPendientes != null ? pedidosPendientes : List.of());
		model.addAttribute("pedidosEnProceso", pedidosEnProceso != null ? pedidosEnProceso : List.of());
		model.addAttribute("pedidosCompletados", pedidosCompletados != null ? pedidosCompletados : List.of());

		return "pedidos_tendero";
	}

	@PostMapping("/pedidos/{id}/procesar")
	public String procesarPedido(@PathVariable Long id, Authentication authentication,
			RedirectAttributes redirectAttributes) {
		try {
			Pedido pedido = pedidoRepository.findById(id)
					.orElseThrow(() -> new RuntimeException("Pedido no encontrado"));
			pedido.setEstado("EN_PROCESO");
			pedidoRepository.save(pedido);
			redirectAttributes.addFlashAttribute("mensaje", "✅ Pedido procesado exitosamente");
			redirectAttributes.addFlashAttribute("tipoMensaje", "success");
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("mensaje", "❌ Error al procesar pedido");
			redirectAttributes.addFlashAttribute("tipoMensaje", "error");
		}
		return "redirect:/tendero/pedidos";
	}

	@PostMapping("/pedidos/{id}/completar")
	public String completarPedido(@PathVariable Long id, Authentication authentication,
			RedirectAttributes redirectAttributes) {
		try {
			Pedido pedido = pedidoRepository.findById(id)
					.orElseThrow(() -> new RuntimeException("Pedido no encontrado"));
			pedido.setEstado("COMPLETADO");
			pedidoRepository.save(pedido);
			redirectAttributes.addFlashAttribute("mensaje", "✅ Pedido completado exitosamente");
			redirectAttributes.addFlashAttribute("tipoMensaje", "success");
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("mensaje", "❌ Error al completar pedido");
			redirectAttributes.addFlashAttribute("tipoMensaje", "error");
		}
		return "redirect:/tendero/pedidos";
	}

	@PostMapping("/pedidos/{id}/cancelar")
	public String cancelarPedido(@PathVariable Long id, Authentication authentication,
			RedirectAttributes redirectAttributes) {
		try {
			Pedido pedido = pedidoRepository.findById(id)
					.orElseThrow(() -> new RuntimeException("Pedido no encontrado"));
			pedido.setEstado("CANCELADO");
			pedidoRepository.save(pedido);
			redirectAttributes.addFlashAttribute("mensaje", "✅ Pedido cancelado exitosamente");
			redirectAttributes.addFlashAttribute("tipoMensaje", "success");
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("mensaje", "❌ Error al cancelar pedido");
			redirectAttributes.addFlashAttribute("tipoMensaje", "error");
		}
		return "redirect:/tendero/pedidos";
	}

	// Los demás métodos permanecen igual...
	@GetMapping("/perfil")
	public String perfil(Authentication authentication, Model model) {
		String username = authentication.getName();
		User user = userRepository.findByEmail(username)
				.orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

		model.addAttribute("username", user.getFirstName() + " " + user.getLastName());
		model.addAttribute("user", user);
		return "perfil_tendero";
	}

	@GetMapping("/ventas")
	public String ventas(Authentication authentication, Model model) {
		String username = authentication.getName();
		User user = userRepository.findByEmail(username)
				.orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

		model.addAttribute("username", user.getFirstName() + " " + user.getLastName());
		return "ventas_tendero";
	}

	@GetMapping("/ingresos")
	public String ingresos(Authentication authentication, Model model) {
		String username = authentication.getName();
		User user = userRepository.findByEmail(username)
				.orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

		model.addAttribute("username", user.getFirstName() + " " + user.getLastName());
		return "ingresos_tendero";
	}

	@GetMapping("/reportes")
	public String reportes(Authentication authentication, Model model) {
		String username = authentication.getName();
		User user = userRepository.findByEmail(username)
				.orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

		model.addAttribute("username", user.getFirstName() + " " + user.getLastName());
		return "reportes_tendero";
	}
}