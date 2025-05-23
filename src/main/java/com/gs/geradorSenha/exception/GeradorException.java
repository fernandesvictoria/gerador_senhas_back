package com.gs.geradorSenha.exception;

import org.springframework.http.HttpStatus;

public class GeradorException extends Exception {
	public GeradorException(String message, HttpStatus badRequest) {
		super(message);
	}
}