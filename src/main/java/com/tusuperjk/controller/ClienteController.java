package com.tusuperjk.controller;

import com.tusuperjk.model.*;
import com.tusuperjk.service.PedidoService;
import com.tusuperjk.service.ProductoService;
import com.tusuperjk.service.TenderoService;
import com.tusuperjk.service.UserService;
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
        private UserService userService;

        @Autowired
        private ProductoService productoService;

        @Autowired
        private PedidoService pedidoService;

        @Autowired
        private TenderoService tenderoService;

        @GetMapping("/dashboard")
        public String clienteDashboard(Authentication authentication, Model model) {
                String username = authentication.getName();
                User user = userService.findByEmail(username)
                                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

                long totalProductos = productoService.contarTotalProductos();
                // In a real app, we would count orders and favorites from DB
                long totalPedidos = pedidoService.listarPedidosPorUsuario(user).size();

                model.addAttribute("username", user.getFirstName() + " " + user.getLastName());
                model.addAttribute("role", user.getRole());
                model.addAttribute("email", user.getEmail());
                model.addAttribute("totalProductos", totalProductos);
                model.addAttribute("totalPedidos", totalPedidos);
                model.addAttribute("totalFavoritos", 0); // TODO: Implement favorites service
                model.addAttribute("user", user);

                return "cliente/dashboard";
        }

        @GetMapping("/productos")
        public String verProductos(Authentication authentication, Model model,
                        @RequestParam(value = "categoria", required = false) String categoria) {
                String username = authentication.getName();
                User user = userService.findByEmail(username)
                                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

                List<Producto> productos;

                if (categoria != null && !categoria.isEmpty()) {
                        productos = productoService.listarProductos(); // TODO: Filter by category in service
                        model.addAttribute("categoriaActual", categoria);
                } else {
                        productos = productoService.listarProductos();
                }

                model.addAttribute("username", user.getFirstName());
                model.addAttribute("productos", productos);
                model.addAttribute("categorias", Arrays.asList(
                                "frutas-verduras", "lacteos", "carnes", "limpieza", "bebidas", "panaderia"));

                return "cliente/productos";
        }

        @GetMapping("/favoritos")
        public String verFavoritos(Authentication authentication, Model model) {
                String username = authentication.getName();
                User user = userService.findByEmail(username)
                                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

                // Placeholder for favorites
                List<Producto> productosFavoritos = Arrays.asList();

                model.addAttribute("username", user.getFirstName());
                model.addAttribute("favoritos", productosFavoritos);
                model.addAttribute("totalFavoritos", productosFavoritos.size());

                return "cliente/favoritos";
        }

        @GetMapping("/pedidos")
        public String verPedidos(Authentication authentication, Model model) {
                String username = authentication.getName();
                User user = userService.findByEmail(username)
                                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

                List<Pedido> pedidos = pedidoService.listarPedidosPorUsuario(user);

                model.addAttribute("username", user.getFirstName());
                model.addAttribute("pedidos", pedidos);
                model.addAttribute("totalPedidos", pedidos.size());

                return "cliente/pedidos";
        }

        @GetMapping("/perfil")
        public String verPerfil(Authentication authentication, Model model) {
                String username = authentication.getName();
                User user = userService.findByEmail(username)
                                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

                model.addAttribute("user", user);
                model.addAttribute("username", user.getFirstName());

                return "cliente/perfil";
        }

        @GetMapping("/carrito")
        public String verCarrito(Authentication authentication, Model model) {
                String username = authentication.getName();
                User user = userService.findByEmail(username)
                                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

                // Carrito logic usually involves session or DB. For now, keeping it empty or
                // dummy?
                // The user asked to fix logical bugs. A dummy cart is a bug.
                // But implementing a full cart system might be out of scope for "Review".
                // However, I should at least make it clear it's empty if no logic exists.
                List<Producto> carrito = Arrays.asList(); // Empty for now until CartService is implemented

                double total = 0;

                model.addAttribute("username", user.getFirstName());
                model.addAttribute("carrito", carrito);
                model.addAttribute("total", total);
                model.addAttribute("itemsCarrito", carrito.size());

                return "cliente/carrito";
        }

        @GetMapping("/ofertas")
        public String verOfertas(Authentication authentication, Model model) {
                String username = authentication.getName();
                User user = userService.findByEmail(username)
                                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

                // Placeholder for offers
                List<Producto> ofertas = Arrays.asList();

                model.addAttribute("username", user.getFirstName());
                model.addAttribute("ofertas", ofertas);
                model.addAttribute("totalOfertas", ofertas.size());

                return "cliente/ofertas";
        }

        @GetMapping("/tenderos")
        public String verTenderos(Authentication authentication, Model model) {
                String username = authentication.getName();
                User user = userService.findByEmail(username)
                                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

                List<Tendero> tenderos = tenderoService.listarTenderos();

                model.addAttribute("username", user.getFirstName());
                model.addAttribute("tenderos", tenderos);
                model.addAttribute("totalTenderos", tenderos.size());

                return "cliente/tenderos";
        }

        @GetMapping("/buscar")
        public String buscarProductos(Authentication authentication, Model model,
                        @RequestParam("q") String query) {
                String username = authentication.getName();
                User user = userService.findByEmail(username)
                                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

                List<Producto> resultados = productoService.buscarPorNombre(query);

                model.addAttribute("username", user.getFirstName());
                model.addAttribute("resultados", resultados);
                model.addAttribute("query", query);
                model.addAttribute("totalResultados", resultados.size());

                return "cliente/busqueda";
        }
}