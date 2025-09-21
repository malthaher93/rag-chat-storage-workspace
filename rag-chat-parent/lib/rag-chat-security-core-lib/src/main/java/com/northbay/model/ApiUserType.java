package com.northbay.model;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.northbay.enums.AuthorizationRoleType;
import com.northbay.util.AuthenticationHelper;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApiUserType implements UserDetails {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6802596182100063442L;

	private String userId;
	
	private String apiKey;

	private boolean active;

	private Collection<AuthorizationRoleType> roles;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return AuthenticationHelper.getRole(roles);
	}

	@Override
	public String getPassword() {
		return apiKey;
	}

	@Override
	public String getUsername() {
		return userId;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return isEnabled();
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return active;
	}
	
}
