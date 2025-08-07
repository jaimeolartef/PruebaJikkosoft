package com.example.pruebajikkosft.view.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ItemPedidoResponse {
	@JsonProperty("id_producto")
	private Integer idProducto;

	@JsonProperty("nombre_producto")
	private String nombreProducto;

	@JsonProperty("cantidad")
	private Integer cantidad;

	@JsonProperty("precio_unitario")
	private BigDecimal precioUnitario;

	@JsonProperty("total_linea")
	private BigDecimal totalLinea;
}
