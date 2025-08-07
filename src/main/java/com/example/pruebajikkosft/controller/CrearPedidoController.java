package com.example.pruebajikkosft.controller;

import com.example.pruebajikkosft.view.dtos.CrearPedidoRequest;
import org.springframework.http.ResponseEntity;

public interface CrearPedidoController {
	/**
	 * Crea un pedido a partir de la solicitud proporcionada.
	 *
	 * @param pedidoRequest DTO que contiene los detalles del pedido a crear.
	 * @return DTO que representa el pedido creado.
	 */
	ResponseEntity crearPedido(CrearPedidoRequest pedidoRequest);
}
