package com.example.pruebajikkosft.controller;

import com.example.pruebajikkosft.config.security.UtilSecurity;
import com.example.pruebajikkosft.model.entities.Usuario;
import com.example.pruebajikkosft.model.repositories.UsuarioRepository;
import com.example.pruebajikkosft.view.dtos.UsuarioDto;
import com.example.pruebajikkosft.view.dtos.CredencialDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class UsuarioControllerImpl implements  UsuarioController{

	private UsuarioRepository usuarioDao;

	public UsuarioControllerImpl(UsuarioRepository usuarioDao) {
		this.usuarioDao = usuarioDao;
	}

	@Override
	public ResponseEntity<CredencialDto> login(CredencialDto credencial) {
		ResponseEntity<CredencialDto> responseEntity;
		Usuario usuario = usuarioDao.findByNombreUsuario(credencial.getUsuario()).get();
		if (credencial.getClave().equals(usuario.getClave())) {
			String token = UtilSecurity.generateToken(credencial.getUsuario());
			responseEntity = ResponseEntity.status(HttpStatus.OK).body(CredencialDto.builder().usuario(credencial.getUsuario()).token(token).build());
			return responseEntity;
		} else {
			responseEntity = ResponseEntity.status(HttpStatus.FORBIDDEN).build();
			return responseEntity;
		}
	}

	@Override
	public ResponseEntity<Void> signup(UsuarioDto usuarioDto) {
		ResponseEntity<Void> responseEntity;
		Usuario usuario = usuarioDao.findByNombreUsuario(usuarioDto.getNombreUsuario()).orElse(null);
		if (usuario == null) {
			usuario = Usuario.builder()
				.nombreUsuario(usuarioDto.getNombreUsuario())
				.nombre(usuarioDto.getNombre())
				.apellido(usuarioDto.getApellido())
				.email(usuarioDto.getEmail())
				.clave(UtilSecurity.encriptar(usuarioDto.getClave()))
				.habilitado(Boolean.TRUE)
				.build();
			usuarioDao.save(usuario);
			responseEntity = ResponseEntity.status(HttpStatus.CREATED).build();
		} else {
			responseEntity = ResponseEntity.status(HttpStatus.CONFLICT).build();
			return responseEntity;
		}
		return responseEntity;
	}
}
