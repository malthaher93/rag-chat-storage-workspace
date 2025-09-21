package com.northbay.exception;

import static com.northbay.constants.GlobalConstants.APPLICATION_JSON_MEDIA_TYPE;
import static com.northbay.constants.GlobalConstants.CODE_400_ERROR_RESPONSE;
import static com.northbay.constants.GlobalConstants.CODE_401_ERROR_RESPONSE;
import static com.northbay.constants.GlobalConstants.CODE_404_ERROR_RESPONSE;
import static com.northbay.constants.GlobalConstants.CODE_429_ERROR_RESPONSE;
import static com.northbay.constants.GlobalConstants.CODE_500_ERROR_RESPONSE;
import static com.northbay.constants.GlobalConstants.DESCRIPTION_400_ERROR_RESPONSE;
import static com.northbay.constants.GlobalConstants.DESCRIPTION_401_ERROR_RESPONSE;
import static com.northbay.constants.GlobalConstants.DESCRIPTION_404_ERROR_RESPONSE;
import static com.northbay.constants.GlobalConstants.DESCRIPTION_429_ERROR_RESPONSE;
import static com.northbay.constants.GlobalConstants.DESCRIPTION_500_ERROR_RESPONSE;
import static com.northbay.constants.GlobalConstants.OBJECT_EXAMPLE_400_ERROR_EXAMPLE;
import static com.northbay.constants.GlobalConstants.OBJECT_EXAMPLE_401_ERROR_RESPONSE;
import static com.northbay.constants.GlobalConstants.OBJECT_EXAMPLE_404_ERROR_EXAMPLE;
import static com.northbay.constants.GlobalConstants.OBJECT_EXAMPLE_429_ERROR_EXAMPLE;
import static com.northbay.constants.GlobalConstants.OBJECT_EXAMPLE_500_ERROR_RESPONSE;
import static com.northbay.util.ResponseUtil.getErrorGenericResponse;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.TOO_MANY_REQUESTS;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.northbay.model.GenericResponseType;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class AbstractServiceExceptionHandler {

	private static final Logger LOG = LoggerFactory.getLogger(AbstractServiceExceptionHandler.class);

	/***
	 * handle exceed rate limit exception
	 * 
	 * @param ex
	 * @return
	 */
	@ApiResponse(responseCode = CODE_429_ERROR_RESPONSE, 
			description = DESCRIPTION_429_ERROR_RESPONSE, 
			content = {@Content(mediaType = APPLICATION_JSON_MEDIA_TYPE, 
			examples = @ExampleObject(value = OBJECT_EXAMPLE_429_ERROR_EXAMPLE), 
			schema = @Schema(implementation = GenericResponseType.class)) })
	@ExceptionHandler(value = { RateLimitErrorException.class })
	@ResponseStatus(value = TOO_MANY_REQUESTS)
	protected ResponseEntity<GenericResponseType<?>> handleRateLimitError(RateLimitErrorException ex) {
		LOG.error(ex.getMessage());
		return new ResponseEntity<>(getResponseBody(ex, TOO_MANY_REQUESTS), getResponesHeader(), TOO_MANY_REQUESTS);
	}

	/***
	 * handle unauthorized exception
	 * 
	 * @param ex
	 * @return
	 */
	@ApiResponse(responseCode = CODE_401_ERROR_RESPONSE, 
			description = DESCRIPTION_401_ERROR_RESPONSE, 
			content = {@Content(mediaType = APPLICATION_JSON_MEDIA_TYPE, 
			examples = @ExampleObject(value = OBJECT_EXAMPLE_401_ERROR_RESPONSE), 
			schema = @Schema(implementation = GenericResponseType.class)) })
	@ExceptionHandler(value = { AuthenticationException.class })
	@ResponseStatus(value = UNAUTHORIZED)
	protected GenericResponseType<?> handleInvalidAuth(AuthenticationException ex) {
		LOG.error(ex.getMessage());
		return getResponseBody(ex, UNAUTHORIZED);
	}

	/***
	 * handle invalid request param exception
	 * 
	 * @param ex
	 * @return
	 */
	@ApiResponse(responseCode = CODE_400_ERROR_RESPONSE, 
			description = DESCRIPTION_400_ERROR_RESPONSE, 
			content = { @Content(mediaType = APPLICATION_JSON_MEDIA_TYPE, 
			examples = @ExampleObject(value = OBJECT_EXAMPLE_400_ERROR_EXAMPLE), 
			schema = @Schema(implementation = GenericResponseType.class)) })
	@ExceptionHandler(value = { MethodArgumentTypeMismatchException.class })
	@ResponseStatus(value = BAD_REQUEST)
	protected ResponseEntity<GenericResponseType<?>> handleInvalidRequest(MethodArgumentTypeMismatchException ex) {
		LOG.error(ex.getMessage());
		return new ResponseEntity<>(getResponseBody(ex, BAD_REQUEST), getResponesHeader(), BAD_REQUEST);
	}

	/***
	 * handle invalid request param exception
	 * 
	 * @param ex
	 * @return
	 */
	@ApiResponse(responseCode = CODE_400_ERROR_RESPONSE, 
			description = DESCRIPTION_400_ERROR_RESPONSE, 
			content = {@Content(mediaType = APPLICATION_JSON_MEDIA_TYPE, 
			examples = @ExampleObject(value = OBJECT_EXAMPLE_400_ERROR_EXAMPLE), 
			schema = @Schema(implementation = GenericResponseType.class)) })
	@ExceptionHandler(value = { IllegalArgumentException.class })
	@ResponseStatus(value = BAD_REQUEST)
	protected ResponseEntity<GenericResponseType<?>> handleInvalidRequest(IllegalArgumentException ex) {
		LOG.error(ex.getMessage());
		return new ResponseEntity<>(getResponseBody(ex, BAD_REQUEST), getResponesHeader(), BAD_REQUEST);
	}

	/***
	 * handle invalid request param exception
	 * 
	 * @param ex
	 * @return
	 */
	@ApiResponse(responseCode = CODE_400_ERROR_RESPONSE, 
			description = DESCRIPTION_400_ERROR_RESPONSE, 
			content = {@Content(mediaType = APPLICATION_JSON_MEDIA_TYPE, 
			examples = @ExampleObject(value = OBJECT_EXAMPLE_400_ERROR_EXAMPLE), 
			schema = @Schema(implementation = GenericResponseType.class)) })
	@ExceptionHandler(value = { ConstraintViolationException.class })
	@ResponseStatus(value = BAD_REQUEST)
	protected ResponseEntity<GenericResponseType<?>> handleInvalidRequest(ConstraintViolationException ex) {
		LOG.error(ex.getMessage());
		return new ResponseEntity<>(getResponseBody(ex, BAD_REQUEST), getResponesHeader(), BAD_REQUEST);
	}

	
	
	/***
	 * handle resource not found exception
	 * 
	 * @param ex
	 * @return
	 */
	@ApiResponse(responseCode = CODE_404_ERROR_RESPONSE, 
			description = DESCRIPTION_404_ERROR_RESPONSE, 
			content = { @Content(mediaType = APPLICATION_JSON_MEDIA_TYPE, 
			examples = @ExampleObject(value = OBJECT_EXAMPLE_404_ERROR_EXAMPLE), 
			schema = @Schema(implementation = GenericResponseType.class)) })
	@ExceptionHandler(value = { ResourceNotFoundException.class })
	@ResponseStatus(value = BAD_REQUEST)
	protected ResponseEntity<GenericResponseType<?>> handleResourceNotFound(ResourceNotFoundException ex) {
		LOG.error(ex.getMessage());
		return new ResponseEntity<>(getResponseBody(ex, NOT_FOUND), getResponesHeader(), BAD_REQUEST);
	}

	/***
	 * handle internal server error
	 * 
	 * @param ex
	 * @return
	 */
	@ApiResponse(responseCode = CODE_500_ERROR_RESPONSE, 
			description = DESCRIPTION_500_ERROR_RESPONSE, 
			content = { @Content(mediaType = APPLICATION_JSON_MEDIA_TYPE,
			examples = @ExampleObject(value = OBJECT_EXAMPLE_500_ERROR_RESPONSE), 
			schema = @Schema(implementation = GenericResponseType.class)) })
	@ExceptionHandler(value = { InternalServerErrorException.class })
	@ResponseStatus(value = INTERNAL_SERVER_ERROR)
	protected ResponseEntity<GenericResponseType<?>> handleInternalServerError(InternalServerErrorException ex) {
		LOG.error(ex.getMessage());
		return new ResponseEntity<>(getResponseBody(ex, INTERNAL_SERVER_ERROR), getResponesHeader(),
				INTERNAL_SERVER_ERROR);
	}

	/***
	 * handle general exception
	 * 
	 * @param ex
	 * @return
	 */
	@ApiResponse(responseCode = CODE_500_ERROR_RESPONSE, 
			description = DESCRIPTION_500_ERROR_RESPONSE, 
			content = { @Content(mediaType = APPLICATION_JSON_MEDIA_TYPE,
			examples = @ExampleObject(value = OBJECT_EXAMPLE_500_ERROR_RESPONSE), 
			schema = @Schema(implementation = GenericResponseType.class)) })
	@ExceptionHandler(value = { Exception.class })
	@ResponseStatus(value = INTERNAL_SERVER_ERROR)
	protected ResponseEntity<GenericResponseType<?>> handleInternalException(Exception ex) {
		LOG.error(ex.getMessage(), ex);
		return new ResponseEntity<>(getResponseBody(ex, INTERNAL_SERVER_ERROR), getResponesHeader(),
				INTERNAL_SERVER_ERROR);
	}

	/***
	 * handle bean validation exception
	 * 
	 * @param ex
	 * @return
	 */
	@ApiResponse(responseCode = CODE_400_ERROR_RESPONSE, 
			description = DESCRIPTION_400_ERROR_RESPONSE, 
			content = {	@Content(mediaType = APPLICATION_JSON_MEDIA_TYPE, 
			examples = @ExampleObject(value = OBJECT_EXAMPLE_400_ERROR_EXAMPLE),
			schema = @Schema(implementation = GenericResponseType.class)) })
	@ExceptionHandler(value = { MethodArgumentNotValidException.class })
	@ResponseStatus(value = BAD_REQUEST)
	public ResponseEntity<GenericResponseType<?>> handleValidationExceptions(MethodArgumentNotValidException ex) {

		LOG.error(ex.getMessage());

		final Map<String, String> errors = ex.getBindingResult().getFieldErrors().stream()
				.collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage));

		return new ResponseEntity<>(getResponseBody(errors.toString(), BAD_REQUEST), getResponesHeader(), BAD_REQUEST);
	}

	/***
	 * handle invalid request body
	 * 
	 * @param ex
	 * @return
	 */
	@ApiResponse(responseCode = CODE_400_ERROR_RESPONSE, 
			description = DESCRIPTION_400_ERROR_RESPONSE, 
			content = { @Content(mediaType = APPLICATION_JSON_MEDIA_TYPE, 
			examples = @ExampleObject(value = OBJECT_EXAMPLE_400_ERROR_EXAMPLE),
			schema = @Schema(implementation = GenericResponseType.class)) })
	@ExceptionHandler(value = { HttpMessageNotReadableException.class })
	@ResponseStatus(value = BAD_REQUEST)
	public ResponseEntity<GenericResponseType<?>> handleInvalidRequestExceptions(HttpMessageNotReadableException ex) {
		LOG.error(ex.getMessage());
		return new ResponseEntity<>(getResponseBody("invalid request body", BAD_REQUEST), getResponesHeader(),
				BAD_REQUEST);
	}

	/***
	 * get response header
	 * 
	 * @return
	 */
	protected HttpHeaders getResponesHeader() {
		final HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		return headers;
	}

	/***
	 * get response body
	 * 
	 * @param ex
	 * @param httpStatus
	 * @return
	 */
	protected GenericResponseType<?> getResponseBody(Exception ex, HttpStatus httpStatus) {
		return getResponseBody(ex.getMessage(), httpStatus);
	}

	/***
	 * get response body
	 * 
	 * @param errorMessage
	 * @param httpStatus
	 * @return
	 */
	protected GenericResponseType<?> getResponseBody(String errorMessage, HttpStatus httpStatus) {
		return getErrorGenericResponse(errorMessage, httpStatus);
	}

}
