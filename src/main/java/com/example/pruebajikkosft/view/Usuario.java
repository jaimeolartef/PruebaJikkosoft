package com.example.pruebajikkosft.view;

import com.example.pruebajikkosft.view.dtos.CredencialDto;
import com.example.pruebajikkosft.view.dtos.UsuarioDto;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import com.example.pruebajikkosft.controller.UsuarioController;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Usuario {

	private final UsuarioController usuarioController;

	public Usuario(UsuarioController usuarioController) {
		this.usuarioController = usuarioController;
	}

	@PostMapping("/user/login")
	public ResponseEntity<CredencialDto> login(@Valid @RequestBody CredencialDto credencial) {
		return usuarioController.login(credencial);
	}

	@PostMapping("/user/signup")
	public ResponseEntity<Void> signup(@Valid @RequestBody UsuarioDto usuarioDto) {
		return usuarioController.signup(usuarioDto);
	}
}
