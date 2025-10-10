package com.tusuperjk.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.tusuperjk.model.Tendero;
import java.util.Optional;

public interface TenderoRepository extends JpaRepository<Tendero, Long> {
    
    // Método adicional para buscar un tendero por su email
    Optional<Tendero> findByEmail(String email);
}
