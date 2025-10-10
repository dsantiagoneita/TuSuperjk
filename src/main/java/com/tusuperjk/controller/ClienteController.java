package com.tusuperjk.controller;

import com.tusuperjk.model.*;
import com.tusuperjk.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/cliente")
public class ClienteController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductoRepository productoRepository;

    @GetMapping("/dashboard")
    public String clienteDashboard(Authentication authentication, Model model) {
        String username = authentication.getName();
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // Estadísticas reales y de ejemplo para el dashboard
        long totalProductos = productoRepository.count();
        
        model.addAttribute("username", user.getFirstName() + " " + user.getLastName());
        model.addAttribute("role", user.getRole());
        model.addAttribute("email", user.getEmail());
        model.addAttribute("totalProductos", totalProductos);
        model.addAttribute("totalPedidos", 3); // Ejemplo - en producción vendría de la BD
        model.addAttribute("totalFavoritos", 5); // Ejemplo - en producción vendría de la BD
        model.addAttribute("user", user); // Para usar en el perfil

        return "cliente/dashboard";
    }

    @GetMapping("/productos")
    public String verProductos(Authentication authentication, Model model,
                             @RequestParam(value = "categoria", required = false) String categoria) {
        String username = authentication.getName();
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        List<Producto> productos;
        
        // Filtrar por categoría si se especifica
        if (categoria != null && !categoria.isEmpty()) {
            // En un sistema real, esto sería una consulta filtrada
            productos = productoRepository.findAll(); // Por ahora mostramos todos
            model.addAttribute("categoriaActual", categoria);
        } else {
            productos = productoRepository.findAll();
        }

        model.addAttribute("username", user.getFirstName());
        model.addAttribute("productos", productos);
        model.addAttribute("categorias", Arrays.asList(
            "frutas-verduras", "lacteos", "carnes", "limpieza", "bebidas", "panaderia"
        ));

        return "cliente/productos";
    }

    @GetMapping("/favoritos")
    public String verFavoritos(Authentication authentication, Model model) {
        String username = authentication.getName();
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // Productos de ejemplo para favoritos (en producción vendrían de la BD)
        List<Producto> productosFavoritos = Arrays.asList(
                new Producto(1L, "Arroz Diana", 2500, 50, "https://via.placeholder.com/300x200?text=Arroz+Diana"),
                new Producto(2L, "Aceite Gourmet", 18000, 25, "https://via.placeholder.com/300x200?text=Aceite+Gourmet"),
                new Producto(3L, "Leche Colanta", 3500, 30, "https://via.placeholder.com/300x200?text=Leche+Colanta"),
                new Producto(4L, "Pan Integral", 4200, 15, "https://via.placeholder.com/300x200?text=Pan+Integral"),
                new Producto(5L, "Café Colina", 12500, 20, "https://via.placeholder.com/300x200?text=Café+Colina")
        );

        model.addAttribute("username", user.getFirstName());
        model.addAttribute("favoritos", productosFavoritos);
        model.addAttribute("totalFavoritos", productosFavoritos.size());

        return "cliente/favoritos";
    }

    @GetMapping("/pedidos")
    public String verPedidos(Authentication authentication, Model model) {
        String username = authentication.getName();
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // Datos de ejemplo para pedidos (en producción vendrían de la BD)
        List<Pedido> pedidos = Arrays.asList(
                new Pedido("PED-001", "2024-01-15", "Entregado", 45.99),
                new Pedido("PED-002", "2024-01-10", "En camino", 32.50),
                new Pedido("PED-003", "2024-01-05", "Entregado", 67.25)
        );

        model.addAttribute("username", user.getFirstName());
        model.addAttribute("pedidos", pedidos);
        model.addAttribute("totalPedidos", pedidos.size());

        return "cliente/pedidos";
    }

    @GetMapping("/perfil")
    public String verPerfil(Authentication authentication, Model model) {
        String username = authentication.getName();
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        model.addAttribute("user", user);
        model.addAttribute("username", user.getFirstName());

        return "cliente/perfil";
    }

    @GetMapping("/carrito")
    public String verCarrito(Authentication authentication, Model model) {
        String username = authentication.getName();
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // Datos de ejemplo para el carrito (en producción vendrían de la BD)
        List<Producto> carrito = Arrays.asList(
                new Producto(1L, "Arroz Diana", 2500, 2, "https://via.placeholder.com/300x200?text=Arroz+Diana"),
                new Producto(2L, "Aceite Gourmet", 18000, 1, "https://via.placeholder.com/300x200?text=Aceite+Gourmet"),
                new Producto(6L, "Azúcar Incauca", 3200, 3, "https://via.placeholder.com/300x200?text=Azúcar+Incauca")
        );

        double total = carrito.stream()
                .mapToDouble(p -> p.getPrecio() * p.getCantidad())
                .sum();

        model.addAttribute("username", user.getFirstName());
        model.addAttribute("carrito", carrito);
        model.addAttribute("total", total);
        model.addAttribute("itemsCarrito", carrito.size());

        return "cliente/carrito";
    }

    @GetMapping("/ofertas")
    public String verOfertas(Authentication authentication, Model model) {
        String username = authentication.getName();
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // Productos en oferta (ejemplo)
        List<Producto> ofertas = Arrays.asList(
                new Producto(7L, "Jabón Rey", 2800, 40, "https://via.placeholder.com/300x200?text=Jabón+Rey"),
                new Producto(8L, "Atún Van Camps", 6500, 15, "https://via.placeholder.com/300x200?text=Atún+Van+Camps"),
                new Producto(9L, "Galletas Festival", 2200, 35, "https://via.placeholder.com/300x200?text=Galletas+Festival"),
                new Producto(10L, "Queso Colanta", 8200, 12, "https://via.placeholder.com/300x200?text=Queso+Colanta")
        );

        model.addAttribute("username", user.getFirstName());
        model.addAttribute("ofertas", ofertas);
        model.addAttribute("totalOfertas", ofertas.size());

        return "cliente/ofertas";
    }

    @GetMapping("/tenderos")
    public String verTenderos(Authentication authentication, Model model) {
        String username = authentication.getName();
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // Datos de ejemplo para tenderos
        List<Tendero> tenderos = Arrays.asList(
                new Tendero("La Esquina", "Calle 123 #45-67", "3001234567", "laesquina@email.com"),
                new Tendero("Mercado Fresco", "Avenida Principal #89-10", "3012345678", "mercadofresco@email.com"),
                new Tendero("Super Vecino", "Diagonal 23 #11-22", "3023456789", "supervecino@email.com"),
                new Tendero("Tienda Don Pedro", "Carrera 56 #78-90", "3034567890", "donpedro@email.com")
        );

        model.addAttribute("username", user.getFirstName());
        model.addAttribute("tenderos", tenderos);
        model.addAttribute("totalTenderos", tenderos.size());

        return "cliente/tenderos";
    }

    // Método auxiliar para búsqueda de productos
    @GetMapping("/buscar")
    public String buscarProductos(Authentication authentication, Model model,
                                @RequestParam("q") String query) {
        String username = authentication.getName();
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // En un sistema real, esto buscaría en la base de datos
        List<Producto> resultados = productoRepository.findByNombreContainingIgnoreCase(query);

        model.addAttribute("username", user.getFirstName());
        model.addAttribute("resultados", resultados);
        model.addAttribute("query", query);
        model.addAttribute("totalResultados", resultados.size());

        return "cliente/busqueda";
    }
}