package com.tusuperjk.repository;

import com.tusuperjk.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {
	List<Pedido> findByEstado(String estado);

	List<Pedido> findByEstadoOrderByFechaCreacionAsc(String estado);

	List<Pedido> findAllByOrderByFechaCreacionDesc();
}