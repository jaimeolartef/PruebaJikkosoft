package com.example.pruebajikkosft.view;

import com.example.pruebajikkosft.controller.CrearPedidoController;
import com.example.pruebajikkosft.view.dtos.CrearPedidoRequest;
import com.example.pruebajikkosft.view.dtos.CredencialDto;
import com.example.pruebajikkosft.view.dtos.PedidoResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/pedidos")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*")
public class CrearPedido {

	@Autowired
    private final CrearPedidoController crearPedidoController;


    @PostMapping("/crear")
    public ResponseEntity<?> crearPedido(@Valid @RequestBody CrearPedidoRequest request) {
        log.info("Iniciando creación de pedido para cliente: {}", request.getIdCliente());
        return crearPedidoController.crearPedido(request);
    }
}