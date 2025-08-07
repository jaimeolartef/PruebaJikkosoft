package com.example.pruebajikkosft.controller;

import com.example.pruebajikkosft.view.dtos.UsuarioDto;
import com.example.pruebajikkosft.view.dtos.CredencialDto;
import org.springframework.http.ResponseEntity;

public interface UsuarioController {

	ResponseEntity<CredencialDto> login(CredencialDto credencial);

	ResponseEntity<Void> signup(UsuarioDto usuarioDto);
}
