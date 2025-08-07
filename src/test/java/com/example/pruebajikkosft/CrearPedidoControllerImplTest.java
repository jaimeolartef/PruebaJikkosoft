package com.example.pruebajikkosft;

import com.example.pruebajikkosft.controller.CrearPedidoControllerImpl;
import com.example.pruebajikkosft.model.entities.*;
import com.example.pruebajikkosft.model.services.*;
import com.example.pruebajikkosft.view.dtos.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CrearPedidoControllerImplTest {

    @Mock
    private ClienteService clienteService;
    @Mock
    private ProductoService productoService;
    @Mock
    private PedidoService pedidoService;
    @Mock
    private ItemPedidoService itemPedidoService;
    @Mock
    private ReglaDescuentoService reglaDescuentoService;

    @InjectMocks
    private CrearPedidoControllerImpl controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void crearPedido_clienteNoExiste_retornaBadRequest() {
        CrearPedidoRequest request = CrearPedidoRequest.builder()
                .idCliente(1)
                .productos(Collections.emptyList())
                .build();

        when(clienteService.findById(1)).thenReturn(Optional.empty());

        ResponseEntity<?> response = controller.crearPedido(request);

        assertEquals(400, response.getStatusCodeValue());
        assertTrue(response.getBody().toString().contains("No existe un cliente"));
    }

    @Test
    void crearPedido_productoNoExiste_retornaBadRequest() {
        CrearPedidoRequest request = CrearPedidoRequest.builder()
                .idCliente(1)
                .productos(List.of(ProductoPedidoDto.builder().idProducto(2).cantidad(1).precio(BigDecimal.valueOf(10000)).build()))
                .build();

        Cliente cliente = Cliente.builder().id(1).build();
        when(clienteService.findById(1)).thenReturn(Optional.of(cliente));
        when(productoService.findById(2)).thenReturn(Optional.empty());

        ResponseEntity<?> response = controller.crearPedido(request);

        assertEquals(400, response.getStatusCodeValue());
        assertTrue(response.getBody().toString().contains("No existe un producto"));
    }

    @Test
    void crearPedido_datosValidos_retornaOk() {
        CrearPedidoRequest request = CrearPedidoRequest.builder()
                .idCliente(1)
                .productos(List.of(ProductoPedidoDto.builder().idProducto(2).cantidad(2).build()))
                .build();

        Cliente cliente = Cliente.builder().id(1).nombre("Juan").direccion("Calle 1").telefono("123").estrato(3).build();
        Producto producto = Producto.builder().id(2).nombre("Prod").precioBase(new BigDecimal("1000")).pesoKg(new BigDecimal("1")).activo(true).build();

        when(clienteService.findById(1)).thenReturn(Optional.of(cliente));
        when(productoService.findById(2)).thenReturn(Optional.of(producto));
        when(reglaDescuentoService.findAll()).thenReturn(Collections.emptyList());
        when(pedidoService.save(any(Pedido.class))).thenAnswer(invocation -> {
            Pedido p = invocation.getArgument(0);
            p.setId(10);
            return p;
        });
        when(itemPedidoService.save(any(ItemPedido.class))).thenReturn(null);

        ResponseEntity<?> response = controller.crearPedido(request);

        assertEquals(200, response.getStatusCodeValue());
        assertTrue(response.getBody().toString().contains("Pedido creado exitosamente"));
    }
}
