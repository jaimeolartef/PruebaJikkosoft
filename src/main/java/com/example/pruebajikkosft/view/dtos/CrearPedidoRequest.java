package com.example.pruebajikkosft.view.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class CrearPedidoRequest {
    @NotNull(message = "El ID del cliente es obligatorio")
    @Positive(message = "El ID del cliente debe ser positivo")
    @JsonProperty("id_cliente")
    private Integer idCliente;

    @NotNull(message = "La lista de productos es obligatoria")
    @NotEmpty(message = "Debe incluir al menos un producto")
    @JsonProperty("productos")
    private List<ProductoPedidoDto> productos;
}
