package com.example.pruebajikkosft.view.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CredencialDto {

	@NotBlank(message = "Usuario no puede ser vacío")
	private String usuario;

	@NotBlank(message = "Clave no puede ser vacía")
	private String clave;

	private String token;
}
