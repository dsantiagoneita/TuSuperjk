package com.tusuperjk.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
public class Tendero {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre no puede estar vacío")
    private String nombre;

    @NotBlank(message = "El apellido no puede estar vacío")
    private String apellido;

    @Email(message = "Debe ser un correo válido")
    @NotBlank(message = "El correo no puede estar vacío")
    @Column(unique = true)
    private String email;

    @NotBlank(message = "La contraseña es obligatoria")
    @Size(min = 6, message = "La contraseña debe tener mínimo 6 caracteres")
    private String password;

    @NotBlank(message = "El nombre de la tienda es obligatorio")
    private String nombreTienda;

    private String direccion;
    private String telefono;

    // Getters y Setters
    // ...
}
