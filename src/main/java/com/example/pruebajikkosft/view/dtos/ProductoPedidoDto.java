package com.example.pruebajikkosft.view.dtos;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductoPedidoDto {

	@NotNull(message = "El ID del producto es obligatorio")
	private Integer idProducto;

	@NotNull(message = "La cantidad es obligatoria")
	@JsonProperty("cantidad")
	private Integer cantidad;

	@NotNull(message = "El precio es obligatorio")
	@DecimalMin(value = "0.0", message = "El precio debe ser mayor o igual a 0")
	@JsonProperty("precio")
	private BigDecimal precio;
}
