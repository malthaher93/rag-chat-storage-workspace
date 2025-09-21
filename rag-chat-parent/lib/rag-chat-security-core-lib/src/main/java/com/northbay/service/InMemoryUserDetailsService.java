package com.northbay.service;

import static com.northbay.util.AuthenticationHelper.getApiUserType;
import static org.apache.commons.lang3.ObjectUtils.isEmpty;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.northbay.exception.AuthenticationException;
import com.northbay.model.ApiUserType;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InMemoryUserDetailsService implements UserDetailsService  {

	@Value("${security.api-keys}")
	private String apiKeys;

	private Map<String, ApiUserType> inMemoryUserDetailsMap;

	@PostConstruct
	public void init() {
		inMemoryUserDetailsMap = Arrays.stream(apiKeys.split(","))
        .map(entry -> entry.split(":"))
        .map(this::mapInMemoryUserDetails)
		.collect(Collectors.toMap(apiUser -> apiUser.getApiKey(), apiUser -> apiUser));
	}

	private ApiUserType mapInMemoryUserDetails(String[] user) {
		return getApiUserType(user);
	}

	@Override
	public ApiUserType loadUserByUsername(String apiKey) {
		if (isEmpty(apiKey))
			throw new AuthenticationException("X_API_KEY is reuqired in the header");

		
		ApiUserType user = inMemoryUserDetailsMap.get(apiKey);
		if (isEmpty(user))
			throw new AuthenticationException("Api key not found");

		return user;
	}
}
