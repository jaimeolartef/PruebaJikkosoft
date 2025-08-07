package com.example.pruebajikkosft.model.entities;

import jakarta.persistence.*;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "USUARIO")
public class Usuario {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", nullable = false)
	private Integer id;

	@Column(name = "NOMBRE_USUARIO", nullable = false)
	private String nombreUsuario;

	@Column(name = "NOMBRE", nullable = false)
	private String nombre;

	@Column(name = "APELLIDO", nullable = false)
	private String apellido;

	@Column(name = "EMAIL", nullable = false)
	private String email;

	@Column(name = "CLAVE", nullable = false)
	private String clave;

	@Column(name = "HABILITADO", nullable = false)
	private Boolean habilitado = false;

}