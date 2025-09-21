package com.northbay.util;

import static com.northbay.enums.AuthorizationRoleType.USER;
import static java.util.Arrays.asList;
import static org.apache.commons.lang3.ObjectUtils.isNotEmpty;
import static org.springframework.security.core.context.SecurityContextHolder.getContext;

import java.util.Collection;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.northbay.enums.AuthorizationRoleType;
import com.northbay.model.ApiKeyAuthenticationTokenType;
import com.northbay.model.ApiUserType;

public class AuthenticationHelper {

	private AuthenticationHelper() {
		throw new IllegalStateException("Utility class");
	}

	/***
	 * un-authenticate user from security context holder
	 * 
	 */
	public static void unauthenticate() {
		if (isNotEmpty(getContext()) && isNotEmpty(getContext().getAuthentication())) {
			getContext().getAuthentication().setAuthenticated(false);
		}
	}

	/***
	 * authenticate user into security context holder
	 * 
	 * @param userName
	 * @param password
	 * @param roles
	 */
	public static void authenticate(Authentication authentication) {
		getContext().setAuthentication(authentication);
	}

	/***
	 * map and get list of roles
	 * 
	 * @param roles
	 * @return
	 */
	public static Collection<SimpleGrantedAuthority> getRole(Collection<AuthorizationRoleType> roles) {
		return roles.stream()
				.map(role -> new SimpleGrantedAuthority(role.name()))
				.collect(Collectors.toList());
	}

	public static Authentication getApiKeyAuthenticationToken(ApiUserType user) {
		return new ApiKeyAuthenticationTokenType(user, getRole(user.getRoles()));
	}

	/***
	 * get header field from the request
	 * 
	 * @param httpServletRequest
	 * @param fieldName
	 * @return
	 */
	public static String getHeader(final HttpServletRequest httpServletRequest, String fieldName) {
		return httpServletRequest.getHeader(fieldName);
	}

	public static ApiKeyAuthenticationTokenType getApiKeyAuthenticationTokenType(String apiKey) {
		return new ApiKeyAuthenticationTokenType(apiKey);
	}
	
	public static ApiUserType getApiUserType(String[] user) {
		return ApiUserType.builder()
				.userId(user[1])
				.active(true)
				.apiKey(user[0])
				.roles(asList(USER))
				.build();
	}
}
