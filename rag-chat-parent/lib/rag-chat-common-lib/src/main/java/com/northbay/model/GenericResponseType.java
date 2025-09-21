package com.northbay.model;

import java.io.Serializable;
import java.util.Date;

import org.springframework.http.HttpStatus;

import com.northbay.enums.GenericResponseStatus;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GenericResponseType<T> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 741838474168256525L;

	@Schema(name = "status", description = "Process status", required = true, type = "GenericResponseStatus", readOnly = true)
	private GenericResponseStatus status;

	@Schema(name = "httpStatus", description = "Http status", example = "OK", required = true, type = "HttpStatus", readOnly = true)
	@Builder.Default
	private HttpStatus httpStatus = HttpStatus.OK;

	@Schema(name = "infoDescription", description = "info description details", example = "info", required = true, type = "String", readOnly = true)
	private String infoDescription;

	@Schema(name = "errorDescription", description = "error description details", example = "error", required = true, type = "String", readOnly = true)
	private String errorDescription;

	@Schema(name = "responseDateTime", description = "response date time", required = true, type = "Date", readOnly = true)
	@Builder.Default
	private Date responseDateTime = new Date();

	@Schema(name = "payload", description = "payload object data", required = true, type = "generic type", readOnly = true)
	private T payload;

}
