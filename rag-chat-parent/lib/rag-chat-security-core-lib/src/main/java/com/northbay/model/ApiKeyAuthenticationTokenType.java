package com.northbay.model;

import java.util.Collection;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class ApiKeyAuthenticationTokenType extends AbstractAuthenticationToken {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3303029798924683317L;
	
	@Schema(name = "API_KEY", description = "user API key", required = true, type = "String")
	private String apiKey;

	@Schema(name = "User", description = "Authenticated API user", required = true, type = "ApiUserType")
	private ApiUserType user;

    public ApiKeyAuthenticationTokenType(String apiKey) {
        super(null);
        this.apiKey = apiKey;
        setAuthenticated(false);
    }

    public ApiKeyAuthenticationTokenType(ApiUserType user , Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.user = user;
        setAuthenticated(true);
    }
    
	@Override
	public Object getCredentials() {
		return apiKey;
	}

	@Override
	public Object getPrincipal() {
		return user;
	}

	@Override
	public Object getDetails() {
		return getPrincipal();
	}
}
