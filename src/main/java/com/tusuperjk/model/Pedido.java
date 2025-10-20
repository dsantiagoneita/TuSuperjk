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

	@Column(nullable = false)
	private String clienteNombre;

	@Column(nullable = false)
	private String clienteTelefono;

	@Column(nullable = false)
	private String direccionEntrega;

	@Column(nullable = false)
	private Double total;

	@Column(nullable = false)
	private String estado; // PENDIENTE, EN_PROCESO, COMPLETADO, CANCELADO

	@Column(nullable = false)
	private LocalDateTime fechaCreacion;

	private LocalDateTime fechaActualizacion;

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "pedido_id")
	private List<ItemPedido> items = new ArrayList<>();

	// Constructores
	public Pedido() {
		this.fechaCreacion = LocalDateTime.now();
		this.estado = "PENDIENTE";
	}

	public Pedido(String clienteNombre, String clienteTelefono, String direccionEntrega, Double total) {
		this();
		this.clienteNombre = clienteNombre;
		this.clienteTelefono = clienteTelefono;
		this.direccionEntrega = direccionEntrega;
		this.total = total;
	}

	// Getters y Setters
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getClienteNombre() {
		return clienteNombre;
	}

	public void setClienteNombre(String clienteNombre) {
		this.clienteNombre = clienteNombre;
	}

	public String getClienteTelefono() {
		return clienteTelefono;
	}

	public void setClienteTelefono(String clienteTelefono) {
		this.clienteTelefono = clienteTelefono;
	}

	public String getDireccionEntrega() {
		return direccionEntrega;
	}

	public void setDireccionEntrega(String direccionEntrega) {
		this.direccionEntrega = direccionEntrega;
	}

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
		this.fechaActualizacion = LocalDateTime.now();
	}

	public LocalDateTime getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(LocalDateTime fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public LocalDateTime getFechaActualizacion() {
		return fechaActualizacion;
	}

	public void setFechaActualizacion(LocalDateTime fechaActualizacion) {
		this.fechaActualizacion = fechaActualizacion;
	}

	public List<ItemPedido> getItems() {
		return items;
	}

	public void setItems(List<ItemPedido> items) {
		this.items = items;
	}

	// Método auxiliar para agregar items
	public void agregarItem(ItemPedido item) {
		this.items.add(item);
	}
}