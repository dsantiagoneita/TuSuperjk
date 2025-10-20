package com.tusuperjk.repository;

import com.tusuperjk.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {
	// Puedes agregar métodos personalizados aquí si los necesitas
}