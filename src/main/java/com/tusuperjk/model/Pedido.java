package com.tusuperjk.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "pedidos")
public class Pedido {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "numero_pedido", unique = true, nullable = false)
	private String numeroPedido;

	@Column(nullable = false)
	private LocalDateTime fecha;

	@Column(nullable = false)
	private String estado;

	@Column(nullable = false)
	private double total;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	@OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<PedidoDetalle> detalles = new ArrayList<>();

	public Pedido() {
	}

	public Pedido(String numeroPedido, LocalDateTime fecha, String estado, double total, User user) {
		this.numeroPedido = numeroPedido;
		this.fecha = fecha;
		this.estado = estado;
		this.total = total;
		this.user = user;
	}

	// Getters y Setters
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNumeroPedido() {
		return numeroPedido;
	}

	public void setNumeroPedido(String numeroPedido) {
		this.numeroPedido = numeroPedido;
	}

	public LocalDateTime getFecha() {
		return fecha;
	}

	public void setFecha(LocalDateTime fecha) {
		this.fecha = fecha;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<PedidoDetalle> getDetalles() {
		return detalles;
	}

	public void setDetalles(List<PedidoDetalle> detalles) {
		this.detalles = detalles;
	}

	public void addDetalle(PedidoDetalle detalle) {
		detalles.add(detalle);
		detalle.setPedido(this);
	}

	public void removeDetalle(PedidoDetalle detalle) {
		detalles.remove(detalle);
		detalle.setPedido(null);
	}
}