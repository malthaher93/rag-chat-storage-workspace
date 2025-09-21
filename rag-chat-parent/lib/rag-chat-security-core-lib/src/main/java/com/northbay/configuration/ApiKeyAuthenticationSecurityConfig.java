package com.northbay.configuration;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.northbay.filter.AuthenticationFilter;
import com.northbay.filter.RateLimitFilter;
import com.northbay.service.ApiKeyAuthenticationProvider;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableWebSecurity
public class ApiKeyAuthenticationSecurityConfig {

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http,AuthenticationFilter authenticationFilter, RateLimitFilter rateLimitFilter) throws Exception {
		return http.authorizeRequests(requests -> requests.antMatchers(whiteListEndpoint()).permitAll()
				.antMatchers(HttpMethod.OPTIONS).permitAll()
				.anyRequest().authenticated())
				.sessionManagement(management -> management.sessionCreationPolicy(STATELESS))
				.csrf(CsrfConfigurer::disable)
				.addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class)
				.addFilterBefore(rateLimitFilter, authenticationFilter.getClass())
				.build();
	}
	
	@Bean
	AuthenticationManager authenticationManager(HttpSecurity http, ApiKeyAuthenticationProvider apiKeyAuthenticationProvider ) throws Exception {
		return http.getSharedObject(AuthenticationManagerBuilder.class)
				.authenticationProvider(apiKeyAuthenticationProvider)
				.build();
	}

	private String[] whiteListEndpoint() {
		return new String[] { "/application/version", "/actuator/**", "/webjars/**", "/v3/api-docs/**","/swagger-ui/**", "/cache/**" };
	}
}