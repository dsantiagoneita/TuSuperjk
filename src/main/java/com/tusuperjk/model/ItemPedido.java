package com.tusuperjk.model;

import jakarta.persistence.*;

@Entity
@Table(name = "items_pedido")
public class ItemPedido {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String productoNombre;

	@Column(nullable = false)
	private Double precioUnitario;

	@Column(nullable = false)
	private Integer cantidad;

	@Column(nullable = false)
	private Double subtotal;

	// Constructores
	public ItemPedido() {
	}

	public ItemPedido(String productoNombre, Double precioUnitario, Integer cantidad) {
		this.productoNombre = productoNombre;
		this.precioUnitario = precioUnitario;
		this.cantidad = cantidad;
		this.subtotal = precioUnitario * cantidad;
	}

	// Getters y Setters
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getProductoNombre() {
		return productoNombre;
	}

	public void setProductoNombre(String productoNombre) {
		this.productoNombre = productoNombre;
	}

	public Double getPrecioUnitario() {
		return precioUnitario;
	}

	public void setPrecioUnitario(Double precioUnitario) {
		this.precioUnitario = precioUnitario;
		this.subtotal = this.precioUnitario * this.cantidad;
	}

	public Integer getCantidad() {
		return cantidad;
	}

	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
		this.subtotal = this.precioUnitario * this.cantidad;
	}

	public Double getSubtotal() {
		return subtotal;
	}

	public void setSubtotal(Double subtotal) {
		this.subtotal = subtotal;
	}
}