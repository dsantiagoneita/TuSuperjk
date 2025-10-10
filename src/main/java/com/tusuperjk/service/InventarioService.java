package com.tusuperjk.service;

import com.tusuperjk.model.Producto;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class InventarioService {
    private final List<Producto> productos = new ArrayList<>();

    public List<Producto> listarProductos() {
        return productos;
    }

    public void guardarProducto(Producto producto) {
        producto.setId((long) (productos.size() + 1));
        productos.add(producto);
    }
}
