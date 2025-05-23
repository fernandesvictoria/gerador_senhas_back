package com.gs.geradorSenha.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.gs.geradorSenha.auth.AuthService;
import com.gs.geradorSenha.exception.GeradorException;
import com.gs.geradorSenha.model.entity.Usuario;
import com.gs.geradorSenha.service.UsuarioService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;

@RestController
@RequestMapping(path = "/auth")
public class AuthController {

	@Autowired
	private AuthService authenticationService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private UsuarioService usuarioService;

	

	@Operation(summary = "Realiza login do usuário", description = "Autentica um usuário e retorna um token de acesso.")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Login realizado com sucesso"),
			@ApiResponse(responseCode = "401", description = "Credenciais inválidas"),
			@ApiResponse(responseCode = "400", description = "Erro de validação nos dados fornecidos") })
	@PostMapping("/login")
	public String login(Authentication authentication) throws GeradorException {
		return authenticationService.authenticate(authentication);
	}

	@Operation(summary = "Cadastra um novo usuário", description = "Cadastra um usuário com o perfil de usuário comum.")
	@ApiResponses(value = { @ApiResponse(responseCode = "201", description = "Usuário cadastrado com sucesso"),
			@ApiResponse(responseCode = "400", description = "Erro de validação") })
	@PostMapping("/novo")
	@ResponseStatus(HttpStatus.CREATED)
	public void cadastrar(@RequestBody Usuario novoUsuario) throws GeradorException {
		try {
			processarCadastro(novoUsuario, false);
		} catch (TransactionSystemException ex) {
		    Throwable cause = ex.getRootCause();
		    if (cause instanceof ConstraintViolationException) {
		        ConstraintViolationException validationEx = (ConstraintViolationException) cause;

		        StringBuilder errorMsg = new StringBuilder("Erro de validação:\n");
		        for (ConstraintViolation<?> violation : validationEx.getConstraintViolations()) {
		            errorMsg.append("- ")
		                    .append(violation.getPropertyPath())
		                    .append(": ")
		                    .append(violation.getMessage())
		                    .append("\n");
		        }

		        throw new GeradorException(errorMsg.toString(), HttpStatus.BAD_REQUEST);
		    } else {
		        throw new GeradorException("Erro na transação: " + ex.getMostSpecificCause().getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		    }
		} catch (Exception ex) {
	        throw new GeradorException(ex.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}


	private void processarCadastro(Usuario usuario, boolean isAdmin) throws GeradorException {
		usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
		usuarioService.cadastrar(usuario);
	}

}