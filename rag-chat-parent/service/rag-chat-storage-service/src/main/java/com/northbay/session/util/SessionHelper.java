package com.northbay.session.util;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

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

}
