package com.northbay.constants;

import lombok.Value;

@Value
public class GlobalConstants {
	
	public static final String SECURITY_SCHEMA_DESCRIPTION = "Fill up with authenticated api key";
	public static final String AUTH_USER_HEADER_ATTRIBUTE_NAME = "X_API_KEY";

	public static final String ERROR_DESCRIPTION_ATTRIBUTE = "errorDescription";
	public static final String ERROR_CODE = "errorCode";

	public static final String OBJECT_EXAMPLE_401_ERROR_RESPONSE = "{ \"status\" : \"FAILED\", \"errorCode\" : \"UNAUTHORIZED\", \"errorDescription\" : \"Unauthenticated user\", \"responseDateTime\" : \"2024-12-24 10:43:57:661\" }";
	public static final String OBJECT_EXAMPLE_403_ERROR_RESPONSE = "{ \"status\" : \"FAILED\", \"errorCode\" : \"FORBIDDEN\", \"errorDescription\" : \"Unauthorized user\", \"responseDateTime\" : \"2024-12-24 10:43:57:661\" }";
	public static final String OBJECT_EXAMPLE_500_ERROR_RESPONSE = "{ \"status\" : \"FAILED\", \"errorCode\" : \"INTERNAL_SERVER_ERROR\", \"errorDescription\" : \"an error message\", \"responseDateTime\" : \"2024-12-24 10:43:57:661\" }";
	public static final String OBJECT_EXAMPLE_400_ERROR_EXAMPLE = "{ \"status\" : \"FAILED\", \"errorCode\" : \"BAD_REQUEST\", \"errorDescription\" : \"an error message\", \"responseDateTime\" : \"2024-12-24 10:43:57:661\" }";
	public static final String OBJECT_EXAMPLE_429_ERROR_EXAMPLE = "{ \"status\" : \"FAILED\", \"errorCode\" : \"TOO_MANY_REQUESTS\", \"errorDescription\" : \"an error message\", \"responseDateTime\" : \"2024-12-24 10:43:57:661\" }";
	public static final String OBJECT_EXAMPLE_404_ERROR_EXAMPLE = "{ \"status\" : \"FAILED\", \"errorCode\" : \"NOT_FOUND\", \"errorDescription\" : \"an error message\", \"responseDateTime\" : \"2024-12-24 10:43:57:661\" }";
	
	public static final String APPLICATION_JSON_MEDIA_TYPE = "application/json";
	public static final String DESCRIPTION_400_ERROR_RESPONSE = "Invalid request";
	public static final String DESCRIPTION_429_ERROR_RESPONSE = "Exceed rate limit";
	public static final String DESCRIPTION_500_ERROR_RESPONSE = "An internal server error during invoke the API";
	public static final String DESCRIPTION_403_ERROR_RESPONSE = "Unauthorized user to invoke the API";
	public static final String DESCRIPTION_401_ERROR_RESPONSE = "Unauthenticated user to invoke the API";
	public static final String DESCRIPTION_404_ERROR_RESPONSE = "Resource does not exist";
	public static final String DESCRIPTION_200_INFO_RETRIEVED_RESPONSE = "Successfully retrieved";

	public static final String CODE_401_ERROR_RESPONSE = "401";
	public static final String CODE_500_ERROR_RESPONSE = "500";
	public static final String CODE_403_ERROR_RESPONSE = "403";
	public static final String CODE_400_ERROR_RESPONSE = "400";
	public static final String CODE_429_ERROR_RESPONSE = "429";
	public static final String CODE_200_INFO_RESPONSE = "200";
	public static final String CODE_201_INFO_RESPONSE = "201";
	public static final String CODE_404_ERROR_RESPONSE = "404";

	public static final String FIELD_IS_MANDATORY = "Field is mandatory";
	public static final String FIELD_IS_MIN_1 = "Value must be greater than or equal to 1";
		
	private GlobalConstants() {
		throw new IllegalStateException("Utility class");
	}

}
