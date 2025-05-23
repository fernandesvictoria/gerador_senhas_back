package com.gs.geradorSenha.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gs.geradorSenha.auth.AuthService;
import com.gs.geradorSenha.exception.GeradorException;
import com.gs.geradorSenha.model.dto.SenhaDTO;
import com.gs.geradorSenha.model.dto.SenhaListagemDTO;
import com.gs.geradorSenha.model.entity.Usuario;
import com.gs.geradorSenha.service.SenhaService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(path = "/senha")
public class SenhaController {

	@Autowired
	private SenhaService senhaService;

	@Autowired
	private AuthService authService;

	@PostMapping
	public ResponseEntity<Void> cadastrarSenha(@Valid @RequestBody SenhaDTO dto) throws GeradorException {
		Usuario usuario = authService.getUsuarioAutenticado();
		senhaService.cadastrarSenha(dto, usuario);
		return ResponseEntity.created(null).build();
	}

	@DeleteMapping(path = "/{idSenha}")
	public void excluirSenha(@PathVariable String idSenha) throws GeradorException {
		senhaService.deletarSenha(idSenha);
	}

	@GetMapping("/senhas")
	public List<SenhaListagemDTO> buscarSenhasDoUsuario() throws GeradorException {
		Usuario usuario = authService.getUsuarioAutenticado();
		return senhaService.buscarSenhasDoUsuario(usuario.getIdUsuario());
	}
}