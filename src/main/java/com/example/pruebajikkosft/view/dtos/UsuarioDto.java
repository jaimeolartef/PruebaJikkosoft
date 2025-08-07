package com.example.pruebajikkosft.view.dtos;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioDto {

	private Integer id;
	private String nombreUsuario;
	private String nombre;
	private String apellido;
	private String email;
	private String clave;
	private Boolean habilitado;
	private String token;
	private Integer idRol;

}
