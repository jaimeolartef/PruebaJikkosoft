package com.example.pruebajikkosft.controller;

import com.example.pruebajikkosft.config.jwt.ErrorResponse;
import com.example.pruebajikkosft.model.entities.*;
import com.example.pruebajikkosft.model.services.*;
import com.example.pruebajikkosft.view.dtos.CrearPedidoRequest;
import com.example.pruebajikkosft.view.dtos.ItemPedidoResponse;
import com.example.pruebajikkosft.view.dtos.PedidoResponse;
import com.example.pruebajikkosft.view.dtos.ProductoPedidoDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class CrearPedidoControllerImpl implements CrearPedidoController {


	private final ClienteService clienteService;
	private final ProductoService productoService;
	private final PedidoService pedidoService;
	private final ItemPedidoService itemPedidoService;
	private final ReglaDescuentoService reglaDescuentoService;

	public CrearPedidoControllerImpl(ClienteService clienteService, ProductoService productoService,
									 PedidoService pedidoService, ItemPedidoService itemPedidoService,
									 ReglaDescuentoService reglaDescuentoService) {
		this.clienteService = clienteService;
		this.productoService = productoService;
		this.pedidoService = pedidoService;
		this.itemPedidoService = itemPedidoService;
		this.reglaDescuentoService = reglaDescuentoService;
	}

	@Override
	public ResponseEntity crearPedido(CrearPedidoRequest pedidoRequest) {

		StringBuilder mensajeError = new StringBuilder();

		List<Producto> productos = new ArrayList<>();
		List<ItemPedido> items = new ArrayList<>();
		BigDecimal subtotal = BigDecimal.ZERO;


		Cliente cliente = validarCliente(pedidoRequest, mensajeError);
		validarProductos(pedidoRequest, productos, mensajeError);

		if (mensajeError.length() > 0) {
			return ResponseEntity.badRequest()
				.body(new ErrorResponse(mensajeError.toString(),400));
		}


		subtotal = cargarDetallePedido(pedidoRequest, productos, items, subtotal);
		BigDecimal descuento = calcularDescuento(cliente, subtotal);
		BigDecimal costoEnvio = calcularCostoEnvio(productos);
		Pedido pedido = armarPedido(pedidoRequest, cliente, subtotal, descuento, costoEnvio);
		guardarPedido(pedido, items);

		return ResponseEntity.ok().body(construirRespuesta(pedido, items, cliente));
	}

	private Cliente validarCliente(CrearPedidoRequest pedidoRequest, StringBuilder mensajeError) {
		Optional<Cliente> clienteOpt = clienteService.findById(pedidoRequest.getIdCliente());
		if (clienteOpt.isEmpty()) {
			mensajeError.append("\nNo existe un cliente con ID: " + pedidoRequest.getIdCliente());
			return null;
		}
		return clienteOpt.get();
	}

	private void validarProductos(CrearPedidoRequest pedidoRequest, List<Producto> productos, StringBuilder mensajeError) {
		for (ProductoPedidoDto productDto : pedidoRequest.getProductos()) {
			Optional<Producto> productoOpt = productoService.findById(productDto.getIdProducto());
			if (productoOpt.isEmpty()) {
				mensajeError.append("\nNo existe un producto con ID: " + productDto.getIdProducto());
			} else if (!productoOpt.get().getActivo()) {
				mensajeError.append("\nEl producto '" + productoOpt.get().getActivo() + "' no está disponible");
			}
			productos.add(productoOpt.orElse(null));
		}
	}

	private BigDecimal calcularDescuento(Cliente cliente, BigDecimal subtotal) {
		BigDecimal descuento = BigDecimal.ZERO;
		List<ReglaDescuento> reglaDescuentos = reglaDescuentoService.findAll();

		for (ReglaDescuento reglaDescuento : reglaDescuentos) {
			if ("MONTO".equals(reglaDescuento.getTipoRegla()) && subtotal.compareTo(reglaDescuento.getMontoMinimo()) > 0) {
				descuento = descuento.add(reglaDescuento.getMontoMinimo());
			} else if ("BASADO_ESTRATO".equals(reglaDescuento.getTipoRegla())
				&& cliente != null
				&& cliente.getEstrato() != null
				&& cliente.getEstrato().equals(reglaDescuento.getMontoMinimo().intValue())) {
				descuento = descuento.add(reglaDescuento.getMontoMinimo());
			}
		}

		return descuento;
	}

	private BigDecimal calcularCostoEnvio(List<Producto> productos) {
		BigDecimal costoEnvio = BigDecimal.ZERO;
		BigDecimal pesoKg = BigDecimal.ZERO;
		for (Producto producto : productos) {
			if (producto.getPesoKg() != null) {
				pesoKg = pesoKg.add(producto.getPesoKg());
			}
		}

		if (pesoKg.compareTo(BigDecimal.ZERO) > 0) {
			if (pesoKg.compareTo(new BigDecimal("5")) <= 0) {
				costoEnvio = new BigDecimal("5000"); // Costo de envío para peso hasta 5kg
			} else if (pesoKg.compareTo(new BigDecimal("10")) <= 0) {
				costoEnvio = new BigDecimal("10000"); // Costo de envío para peso entre 5kg y 10kg
			} else {
				costoEnvio = new BigDecimal("15000"); // Costo de envío para peso mayor a 10kg
			}
		}

		return costoEnvio;
	}

	private Pedido armarPedido(CrearPedidoRequest pedidoRequest, Cliente cliente, BigDecimal subtotal, BigDecimal descuento, BigDecimal costoEnvio) {
		return Pedido.builder()
			.idCliente(Cliente.builder().id(cliente.getId()).build())
			.subtotal(subtotal)
			.montoDescuento(descuento)
			.costoEnvio(costoEnvio)
			.total(subtotal.subtract(descuento).add(costoEnvio))
			.estadoPedido("CONFIRMADO")
			.fechaCreacion(Instant.now())
			.build();
	}

	private BigDecimal cargarDetallePedido(CrearPedidoRequest pedidoRequest, List<Producto> productos, List<ItemPedido> items, BigDecimal subtotal) {
		ItemPedido item;
		for (ProductoPedidoDto productoDto : pedidoRequest.getProductos()) {
			Optional<Producto> productoOpt = productos.stream()
				.filter(p -> p.getId().equals(productoDto.getIdProducto()))
				.findFirst();
			if (productoOpt.isPresent()) {
				Producto producto = productoOpt.get();
				item = new ItemPedido();
				item.setIdProducto(Producto.builder().id(producto.getId()).build());
				item.setCantidad(productoDto.getCantidad());
				item.setPrecioUnitario(producto.getPrecioBase());
				item.setTotalLinea(producto.getPrecioBase().multiply(BigDecimal.valueOf(productoDto.getCantidad())));
				items.add(item);
				subtotal = subtotal.add(item.getTotalLinea());
			}
		}

		return subtotal;
	}

	private PedidoResponse construirRespuesta(Pedido pedido, List<ItemPedido> items, Cliente cliente) {
		List<ItemPedidoResponse> itemResponses = new ArrayList<>();
		for (ItemPedido item : items) {
			ItemPedidoResponse itemResponse = ItemPedidoResponse.builder()
				.idProducto(item.getIdProducto().getId())
				.nombreProducto(item.getIdProducto().getNombre())
				.cantidad(item.getCantidad())
				.precioUnitario(item.getPrecioUnitario())
				.totalLinea(item.getTotalLinea())
				.build();
			itemResponses.add(itemResponse);
		}

		return PedidoResponse.builder()
			.idPedido(pedido.getId())
			.nombreCliente(cliente.getNombre())
			.direccionEntrega(cliente.getDireccion())
			.telefonoContacto(cliente.getTelefono())
			.subtotal(pedido.getSubtotal())
			.costoEnvio(pedido.getCostoEnvio())
			.descuentoAplicado(pedido.getMontoDescuento())
			.total(pedido.getTotal())
			.estado(pedido.getEstadoPedido())
			.productos(itemResponses)
			.mensaje("Pedido creado exitosamente")
			.build();
	}

	private void guardarPedido(Pedido pedido, List<ItemPedido> items) {
		Pedido nuevoPedido = pedidoService.save(pedido);
		for (ItemPedido item : items) {
			item.setIdPedido(nuevoPedido);
			itemPedidoService.save(item);
		}
		pedido.setId(nuevoPedido.getId());
	}
}
