package com.northbay.exception;

import static com.northbay.constants.GlobalConstants.APPLICATION_JSON_MEDIA_TYPE;
import static com.northbay.constants.GlobalConstants.CODE_403_ERROR_RESPONSE;
import static com.northbay.constants.GlobalConstants.DESCRIPTION_403_ERROR_RESPONSE;
import static com.northbay.constants.GlobalConstants.OBJECT_EXAMPLE_403_ERROR_RESPONSE;
import static org.springframework.http.HttpStatus.FORBIDDEN;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.northbay.model.GenericResponseType;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestControllerAdvice
public class ServiceExceptionHandler extends AbstractServiceExceptionHandler {

	private static final Logger LOG = LoggerFactory.getLogger(ServiceExceptionHandler.class);

	/***
	 * handle AccessDeniedException
	 * 
	 * @param ex
	 * @return
	 */
	@ApiResponse(responseCode = CODE_403_ERROR_RESPONSE, 
			description = DESCRIPTION_403_ERROR_RESPONSE, 
			content = { @Content(mediaType = APPLICATION_JSON_MEDIA_TYPE, 
			examples = @ExampleObject(value = OBJECT_EXAMPLE_403_ERROR_RESPONSE), 
			schema = @Schema(implementation = GenericResponseType.class))})
	@ExceptionHandler(value = { AccessDeniedException.class })
	@ResponseStatus(value = FORBIDDEN)
	protected GenericResponseType<?> handleInvalidAuth(AccessDeniedException ex) {
		LOG.error(ex.getMessage());
		return getResponseBody(ex, FORBIDDEN);
	}

}
