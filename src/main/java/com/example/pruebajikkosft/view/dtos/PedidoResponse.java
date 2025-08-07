package com.example.pruebajikkosft.view.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PedidoResponse {
	private Integer idPedido;
	private String nombreCliente;
	private String direccionEntrega;
	private String telefonoContacto;
	private BigDecimal subtotal;
	private BigDecimal costoEnvio;
	private BigDecimal descuentoAplicado;
	private BigDecimal total;
	private String estado;
	private List<ItemPedidoResponse> productos;
	private String mensaje;
}
