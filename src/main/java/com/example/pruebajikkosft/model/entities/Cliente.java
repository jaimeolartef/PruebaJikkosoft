package com.example.pruebajikkosft.model.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;

@Builder
@Getter
@Setter
@Entity
@Table(name = "CLIENTE")
@NoArgsConstructor
@AllArgsConstructor
public class Cliente {
	@Id
	@Column(name = "ID_CLIENTE", nullable = false)
	private Integer id;

	@Column(name = "NOMBRE", nullable = false, length = 100)
	private String nombre;

	@Column(name = "EMAIL", nullable = false, length = 100)
	private String email;

	@Column(name = "TELEFONO", length = 20)
	private String telefono;

	@Column(name = "ESTRATO")
	private Integer estrato;

	@Column(name = "DIRECCION")
	private String direccion;

	@ColumnDefault("CURRENT_TIMESTAMP")
	@Column(name = "FECHA_CREACION")
	private Instant fechaCreacion;

	@ColumnDefault("CURRENT_TIMESTAMP")
	@Column(name = "FECHA_ACTUALIZACION")
	private Instant fechaActualizacion;

}