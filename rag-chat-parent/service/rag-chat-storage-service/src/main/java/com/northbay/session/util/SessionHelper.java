package com.northbay.session.util;

import static com.northbay.session.constant.SessionConstant.INVALID_API_USER;

import java.util.Optional;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import com.northbay.exception.AuthenticationException;
import com.northbay.model.ApiUserType;

public class SessionHelper {

	private SessionHelper() {
		throw new IllegalStateException("Utility class");
	}

	/***
	 * get page request
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	public static PageRequest getPageRequest(Integer pageIndex, Integer pageSize) {
		return PageRequest.of(pageIndex-1, pageSize, Sort.by("id").descending());
	}
	

	public static String getUserId(ApiUserType user) {
		return Optional.ofNullable(user)
				.map(ApiUserType::getUserId)
				.orElseThrow(() -> new AuthenticationException(INVALID_API_USER));
	}


}
