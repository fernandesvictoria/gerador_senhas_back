package com.gs.geradorSenha.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.gs.geradorSenha.exception.GeradorException;
import com.gs.geradorSenha.model.entity.Usuario;
import com.gs.geradorSenha.model.repository.UsuarioRepository;

@Service
public class UsuarioService implements UserDetailsService {

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Usuario usuario = usuarioRepository.findByEmail(username)
				.orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado: " + username));

		return (UserDetails) usuario;
	}

	public void cadastrar(Usuario usuario) throws GeradorException {
		if (usuarioRepository.existsByEmailIgnoreCase(usuario.getEmail())) {
			throw new GeradorException("O e-mail informado já está cadastrado.", HttpStatus.BAD_REQUEST);
		}
		usuarioRepository.save(usuario);
	}

	public Usuario pesquisarPorId(Long id) throws GeradorException {
		return usuarioRepository.findById(id)
				.orElseThrow(() -> new GeradorException("Usuário não encontrado.", HttpStatus.NOT_FOUND));
	}

}