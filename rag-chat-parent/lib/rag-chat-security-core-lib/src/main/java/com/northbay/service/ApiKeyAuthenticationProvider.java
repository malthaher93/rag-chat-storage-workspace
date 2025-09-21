package com.northbay.service;

import static com.northbay.util.AuthenticationHelper.*;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.northbay.exception.AuthenticationException;
import com.northbay.model.ApiKeyAuthenticationTokenType;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ApiKeyAuthenticationProvider  implements AuthenticationProvider {

	private final InMemoryUserDetailsService inMemoryUserDetailsService;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		try {
			return getApiKeyAuthenticationToken(inMemoryUserDetailsService.loadUserByUsername((String) authentication.getCredentials()));
		} catch (Exception e) {
			throw new AuthenticationException(e.getMessage());
		}
	}
	
	@Override
	public boolean supports(Class<?> authentication) {
		return ApiKeyAuthenticationTokenType.class.isAssignableFrom(authentication);
	}
}
