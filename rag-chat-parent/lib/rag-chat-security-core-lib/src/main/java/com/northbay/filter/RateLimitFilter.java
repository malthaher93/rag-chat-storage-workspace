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
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import com.northbay.exception.RateLimitErrorException;

import io.github.resilience4j.ratelimiter.RateLimiter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class RateLimitFilter extends OncePerRequestFilter {

	private final List<String> whiteListEndpoint = Arrays.asList("swagger-ui", "api-docs", "application/version","actuator", "webjars");
	private final RateLimiter rateLimiter;
	private final HandlerExceptionResolver handlerExceptionResolver;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)throws IOException, ServletException {

		if (!rateLimiter.acquirePermission()) {
			onRateLimitError(request, response, new RateLimitErrorException("Too many requests"));
			return;
		}				
		filterChain.doFilter(request, response);
	}
	
	/***
	 * 
	 * @param request
	 * @param response
	 * @param exception
	 */
	private void onRateLimitError(HttpServletRequest request, HttpServletResponse response, RateLimitErrorException exception) {
		handlerExceptionResolver.resolveException(request, response, null, exception);
		response.setStatus(429);
	}


	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
		return equalsIgnoreCase(request.getMethod(), HttpMethod.OPTIONS.name()) || whiteListEndpoint.stream().anyMatch(request.getRequestURI()::contains);
	}
}
