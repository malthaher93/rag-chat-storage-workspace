package com.northbay.util;

import org.springframework.http.HttpStatus;

import com.northbay.enums.GenericResponseStatus;
import com.northbay.model.GenericResponseType;

public class ResponseUtil {

	private ResponseUtil() {
		throw new IllegalStateException("Utility class");
	}

	public static GenericResponseType<?> getErrorGenericResponse(String errorMessage, HttpStatus httpStatus) {
		return GenericResponseType.builder()
				.status(GenericResponseStatus.FAILED)
				.httpStatus(httpStatus)
				.errorDescription(errorMessage)
				.build();
	}
	
	public static GenericResponseType<?> getInfoGenericResponse(HttpStatus httpStatus, String infoMessage) {
		return GenericResponseType.builder()
				.status(GenericResponseStatus.SUCCESS)
				.httpStatus(httpStatus)
				.infoDescription(infoMessage)
				.build();
	}

}
