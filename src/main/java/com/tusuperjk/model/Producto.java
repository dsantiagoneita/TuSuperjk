package com.tusuperjk.model;

import jakarta.persistence.*;

@Entity
@Table(name = "productos")
public class Producto {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String nombre;
	private double precio;
	private int cantidad;

	// Nuevo campo para la URL de la imagen
	@Column(name = "imagen_url") // Asegúrate de que el nombre de la columna coincida con tu BD; ajusta si es
									// necesario
	private String imagenUrl;

	// Constructor vacío (actualizado para incluir el nuevo campo, aunque no es
	// obligatorio)
	public Producto() {
	}

	// Constructor con parámetros (actualizado para incluir imagenUrl)
	public Producto(Long id, String nombre, double precio, int cantidad, String imagenUrl) {
		this.id = id;
		this.nombre = nombre;
		this.precio = precio;
		this.cantidad = cantidad;
		this.imagenUrl = imagenUrl;
	}

	// Getters y Setters existentes
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public double getPrecio() {
		return precio;
	}

	public void setPrecio(double precio) {
		this.precio = precio;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	// Nuevo getter y setter para imagenUrl
	public String getImagenUrl() {
		return imagenUrl;
	}

	public void setImagenUrl(String imagenUrl) {
		this.imagenUrl = imagenUrl;
	}
}
