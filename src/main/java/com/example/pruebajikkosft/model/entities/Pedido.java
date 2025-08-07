package com.example.pruebajikkosft.model.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.math.BigDecimal;
import java.time.Instant;

@Builder
@Getter
@Setter
@Entity
@Table(name = "PEDIDO")
@NoArgsConstructor
@AllArgsConstructor
public class Pedido {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_PEDIDO", nullable = false)
	private Integer id;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@OnDelete(action = OnDeleteAction.RESTRICT)
	@JoinColumn(name = "ID_CLIENTE", nullable = false)
	private Cliente idCliente;

	@ColumnDefault("0")
	@Column(name = "SUBTOTAL", nullable = false, precision = 12, scale = 2)
	private BigDecimal subtotal;

	@ColumnDefault("0")
	@Column(name = "COSTO_ENVIO", nullable = false, precision = 8, scale = 2)
	private BigDecimal costoEnvio;

	@ColumnDefault("0")
	@Column(name = "MONTO_DESCUENTO", nullable = false, precision = 10, scale = 2)
	private BigDecimal montoDescuento;

	@ColumnDefault("0")
	@Column(name = "TOTAL", nullable = false, precision = 12, scale = 2)
	private BigDecimal total;

	@ColumnDefault("'PENDIENTE'")
	@Column(name = "ESTADO_PEDIDO", length = 20)
	private String estadoPedido;

	@ColumnDefault("CURRENT_TIMESTAMP")
	@Column(name = "FECHA_CREACION")
	private Instant fechaCreacion;

	@ColumnDefault("CURRENT_TIMESTAMP")
	@Column(name = "FECHA_ACTUALIZACION")
	private Instant fechaActualizacion;

}