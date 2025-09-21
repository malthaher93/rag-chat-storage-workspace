package com.northbay.exception;

import lombok.Getter;

@Getter
public class ResourceNotFoundException extends RuntimeException {

	private static final long serialVersionUID = -3805301178233717292L;
	private final String message;

	public ResourceNotFoundException(String message) {
		super(message);
		this.message = message;
	}
}
