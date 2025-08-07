package com.example.pruebajikkosft.model.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.math.BigDecimal;
import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "ITEM_PEDIDO")
@NoArgsConstructor
@AllArgsConstructor
public class ItemPedido {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_ITEM_PEDIDO", nullable = false)
	private Integer id;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JoinColumn(name = "ID_PEDIDO", nullable = false)
	private Pedido idPedido;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@OnDelete(action = OnDeleteAction.RESTRICT)
	@JoinColumn(name = "ID_PRODUCTO", nullable = false)
	private Producto idProducto;

	@Column(name = "CANTIDAD", nullable = false)
	private Integer cantidad;

	@Column(name = "PRECIO_UNITARIO", nullable = false, precision = 10, scale = 2)
	private BigDecimal precioUnitario;

	@Column(name = "TOTAL_LINEA", nullable = false, precision = 12, scale = 2)
	private BigDecimal totalLinea;

	@ColumnDefault("CURRENT_TIMESTAMP")
	@Column(name = "FECHA_CREACION")
	private Instant fechaCreacion;

}