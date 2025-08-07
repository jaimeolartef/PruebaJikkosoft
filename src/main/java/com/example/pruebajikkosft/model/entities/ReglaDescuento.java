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
@Table(name = "REGLA_DESCUENTO")
@NoArgsConstructor
@AllArgsConstructor
public class ReglaDescuento {
	@Id
	@Column(name = "ID_REGLA_DESCUENTO", nullable = false)
	private Integer id;

	@Column(name = "NOMBRE_REGLA", nullable = false, length = 100)
	private String nombreRegla;

	@Column(name = "TIPO_REGLA", nullable = false, length = 20)
	private String tipoRegla;

	@ColumnDefault("0")
	@Column(name = "MONTO_MINIMO", precision = 10, scale = 2)
	private BigDecimal montoMinimo;

	@Column(name = "VALOR_DESCUENTO", nullable = false, precision = 8, scale = 2)
	private BigDecimal valorDescuento;

	@ColumnDefault("TRUE")
	@Column(name = "ACTIVA")
	private Boolean activa;

	@ColumnDefault("CURRENT_TIMESTAMP")
	@Column(name = "FECHA_CREACION")
	private Instant fechaCreacion;

}