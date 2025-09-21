package com.northbay.exception;

import javax.servlet.ServletRequest;

import com.northbay.constants.GlobalConstants;

import lombok.Getter;

@Getter
public class AuthenticationException extends RuntimeException {

	private static final long serialVersionUID = -3805301178233717292L;
	private final String message;

	public AuthenticationException(ServletRequest req) {
		super((String) req.getAttribute(GlobalConstants.ERROR_DESCRIPTION_ATTRIBUTE));
		this.message = (String) req.getAttribute(GlobalConstants.ERROR_DESCRIPTION_ATTRIBUTE);
	}

	public AuthenticationException(String message) {
		super(message);
		this.message = message;
	}
}
