package com.gs.geradorSenha.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import com.gs.geradorSenha.exception.GSException;
import com.gs.geradorSenha.model.entity.Usuario;
import com.gs.geradorSenha.model.repository.UsuarioRepository;

@Service
public class AuthService {

	private final JwtService jwtService;

	@Autowired
	private UsuarioRepository userRepository;

	public AuthService(JwtService jwtService) {
		this.jwtService = jwtService;
	}

	public String authenticate(Authentication authentication) {
		return jwtService.generateToken(authentication);
	}

	public Usuario getUsuarioAutenticado() throws GSException {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Usuario authenticatedUser = null;

		if (authentication != null && authentication.isAuthenticated()) {
			Object principal = authentication.getPrincipal();

			Jwt jwt = (Jwt) principal;
			String login = jwt.getClaim("sub");

			authenticatedUser = userRepository.findByEmail(login)
					.orElseThrow(() -> new GSException("Usuário não encontrado.", HttpStatus.BAD_REQUEST));
		}

		if (authenticatedUser == null) {
			throw new GSException("Usuário não encontrado.", HttpStatus.BAD_REQUEST);
		}

		return authenticatedUser;
	}
}