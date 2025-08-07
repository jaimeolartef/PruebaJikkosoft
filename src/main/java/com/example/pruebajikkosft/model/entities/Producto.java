package com.example.pruebajikkosft.model.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.math.BigDecimal;
import java.time.Instant;

@Builder
@Getter
@Setter
@Entity
@Table(name = "PRODUCTO")
@NoArgsConstructor
@AllArgsConstructor
public class Producto {
	@Id
	@Column(name = "ID_PRODUCTO", nullable = false)
	private Integer id;

	@Column(name = "NOMBRE", nullable = false, length = 150)
	private String nombre;

	@Column(name = "DESCRIPCION", length = 500)
	private String descripcion;

	@Column(name = "PRECIO_BASE", nullable = false, precision = 10, scale = 2)
	private BigDecimal precioBase;

	@Column(name = "CATEGORIA", length = 50)
	private String categoria;

	@ColumnDefault("TRUE")
	@Column(name = "ACTIVO")
	private Boolean activo;

	@ColumnDefault("0.000")
	@Column(name = "PESO_KG", precision = 8, scale = 3)
	private BigDecimal pesoKg;

	@ColumnDefault("CURRENT_TIMESTAMP")
	@Column(name = "FECHA_CREACION")
	private Instant fechaCreacion;

	@ColumnDefault("CURRENT_TIMESTAMP")
	@Column(name = "FECHA_ACTUALIZACION")
	private Instant fechaActualizacion;

}