package com.northbay.filter;

import static org.apache.commons.lang3.StringUtils.equalsIgnoreCase;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import com.northbay.exception.AuthenticationException;
import com.northbay.model.ApiKeyAuthenticationTokenType;
import com.northbay.util.AuthenticationHelper;

import static com.northbay.util.AuthenticationHelper.*;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class AuthenticationFilter extends OncePerRequestFilter {

	private final List<String> whiteListEndpoint = Arrays.asList("swagger-ui", "api-docs", "application/version","actuator", "webjars", "cache");
	
	private final AuthenticationManager authenticationManager;
	private final HandlerExceptionResolver handlerExceptionResolver;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)throws IOException, ServletException {
		
        try {
        	authenticate(request);
        } catch (AuthenticationException ex) {
        	unauthenticate(request, response, ex);  
            return;
        }
        
        filterChain.doFilter(request, response);
	}

	private void authenticate(HttpServletRequest request) {
		AuthenticationHelper.authenticate(authenticationManager.authenticate(getApiKeyAuthenticationTokenType(request)));
	}
	
	private ApiKeyAuthenticationTokenType getApiKeyAuthenticationTokenType(HttpServletRequest request) {
    	return AuthenticationHelper.getApiKeyAuthenticationTokenType(getApiKey(request));
	}

	private String getApiKey(HttpServletRequest request) {
		return getHeader(request, "X_API_KEY");
	}
	
	/***
	 * 
	 * @param request
	 * @param response
	 * @param exception
	 */
	private void unauthenticate(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) {
		AuthenticationHelper.unauthenticate();
		handlerExceptionResolver.resolveException(request, response, null, exception);
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
	}
	

	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
		return equalsIgnoreCase(request.getMethod(), HttpMethod.OPTIONS.name()) || whiteListEndpoint.stream().anyMatch(request.getRequestURI()::contains);
	}
}
