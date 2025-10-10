package com.tusuperjk.model;

public class Pedido {
	private String numeroPedido;
	private String fecha;
	private String estado;
	private double total;

	public Pedido() {
	}

	public Pedido(String numeroPedido, String fecha, String estado, double total) {
		this.numeroPedido = numeroPedido;
		this.fecha = fecha;
		this.estado = estado;
		this.total = total;
	}

	// Getters y Setters
	public String getNumeroPedido() {
		return numeroPedido;
	}

	public void setNumeroPedido(String numeroPedido) {
		this.numeroPedido = numeroPedido;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
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
}