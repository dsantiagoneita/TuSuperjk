package com.tusuperjk.repository;

import com.tusuperjk.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;  
@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {
    long countByCantidadLessThan(int cantidad);
    List<Producto> findByNombreContainingIgnoreCase(String nombre);
}