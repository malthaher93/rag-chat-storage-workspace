package com.northbay.session.controller;

import static com.northbay.constants.GlobalConstants.APPLICATION_JSON_MEDIA_TYPE;
import static com.northbay.constants.GlobalConstants.CODE_200_INFO_RESPONSE;
import static com.northbay.constants.GlobalConstants.CODE_201_INFO_RESPONSE;
import static com.northbay.session.constant.SessionConstant.CODE_200_INFO_DELETE_RESPONSE_DESC;
import static com.northbay.session.constant.SessionConstant.CODE_200_INFO_RETRIEVE_RESPONSE_DESC;
import static com.northbay.session.constant.SessionConstant.CODE_200_INFO_UPDATE_RESPONSE_DESC;
import static com.northbay.session.constant.SessionConstant.CODE_201_INFO_RESPONSE_DESC;
import static com.northbay.session.constant.SessionConstant.CREATE_SESSION_API;
import static com.northbay.session.constant.SessionConstant.CREATE_SESSION_API_DESC;
import static com.northbay.session.constant.SessionConstant.DELETE_SESSION_API;
import static com.northbay.session.constant.SessionConstant.DELETE_SESSION_API_DESC;
import static com.northbay.session.constant.SessionConstant.FIND_ALL_SESSIONS_API;
import static com.northbay.session.constant.SessionConstant.FIND_ALL_SESSIONS_API_DESC;
import static com.northbay.session.constant.SessionConstant.FIND_SESSION_BY_ID_API;
import static com.northbay.session.constant.SessionConstant.FIND_SESSION_BY_ID_API_DESC;
import static com.northbay.session.constant.SessionConstant.FIND_SESSION_BY_ID_API_PATH;
import static com.northbay.session.constant.SessionConstant.OBJECT_EXAMPLE_CREATE_SESSION_API_REQ;
import static com.northbay.session.constant.SessionConstant.OBJECT_EXAMPLE_CREATE_SESSION_API_RESP;
import static com.northbay.session.constant.SessionConstant.OBJECT_EXAMPLE_DELETE_SESSION_API_RESP;
import static com.northbay.session.constant.SessionConstant.SESSION_API_AUTH;
import static com.northbay.session.constant.SessionConstant.SESSION_CONTROLLER;
import static com.northbay.session.constant.SessionConstant.SESSION_CONTROLLER_DESC;
import static com.northbay.session.constant.SessionConstant.TOGGLE_FAVORITE_SESSION_API;
import static com.northbay.session.constant.SessionConstant.TOGGLE_FAVORITE_SESSION_API_DESC;
import static com.northbay.session.constant.SessionConstant.TOGGLE_FAVORITE_SESSION_API_PATH;
import static com.northbay.session.constant.SessionConstant.UPDATE_SESSION_API;
import static com.northbay.session.constant.SessionConstant.UPDATE_SESSION_API_DESC;

import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.northbay.common.controller.AbstractChatController;
import com.northbay.model.GenericResponseType;
import com.northbay.session.model.SessionType;
import com.northbay.session.service.SessionService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@Tag(name = SESSION_CONTROLLER, description = SESSION_CONTROLLER_DESC)
@RequiredArgsConstructor
public class SessionController extends AbstractChatController {

	private final SessionService sessionService;
	
	@Operation(summary = CREATE_SESSION_API, 
			description = CREATE_SESSION_API_DESC, 
			responses = { @ApiResponse(responseCode = CODE_201_INFO_RESPONSE, 
			description = CODE_201_INFO_RESPONSE_DESC,
			content = { @Content(mediaType = APPLICATION_JSON_MEDIA_TYPE, 
			examples = @ExampleObject(value = OBJECT_EXAMPLE_CREATE_SESSION_API_RESP), 
			schema = @Schema(implementation = SessionType.class)) }) },
			requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(mediaType = APPLICATION_JSON_MEDIA_TYPE, 
			examples = @ExampleObject(value = OBJECT_EXAMPLE_CREATE_SESSION_API_REQ), 
			schema = @Schema(implementation = SessionType.class))))
	@PostMapping
	@PreAuthorize(SESSION_API_AUTH)
	public SessionType create(@RequestBody SessionType request) {
		return sessionService.create(request);
	}

	@Operation(summary = FIND_ALL_SESSIONS_API, 
			description = FIND_ALL_SESSIONS_API_DESC, 
			responses = { @ApiResponse(responseCode = CODE_200_INFO_RESPONSE, 
			description = CODE_200_INFO_RETRIEVE_RESPONSE_DESC, 
			content = { @Content(mediaType = APPLICATION_JSON_MEDIA_TYPE, 
			schema = @Schema(implementation = SessionType.class)) }) })
	@GetMapping
	@PreAuthorize(SESSION_API_AUTH)
	public Page<SessionType> findAll(@RequestParam(required = false, defaultValue = "1") Integer pageIndex, 
			@RequestParam(required = false, defaultValue = "10") Integer pageSize) {
		return sessionService.findAll(pageIndex, pageSize);
	}

	@Operation(summary = FIND_SESSION_BY_ID_API, 
			description = FIND_SESSION_BY_ID_API_DESC, 
			responses = { @ApiResponse(responseCode = CODE_200_INFO_RESPONSE, 
			description = CODE_200_INFO_RETRIEVE_RESPONSE_DESC, 
			content = { @Content(mediaType = APPLICATION_JSON_MEDIA_TYPE, 
			schema = @Schema(implementation = SessionType.class)) }) })
	@GetMapping(FIND_SESSION_BY_ID_API_PATH)
	@PreAuthorize(SESSION_API_AUTH)
	public SessionType findById(@PathVariable String sessionId) {
		return sessionService.findById(sessionId);
	}

	@Operation(summary = UPDATE_SESSION_API, 
			description = UPDATE_SESSION_API_DESC, 
			responses = { @ApiResponse(responseCode = CODE_200_INFO_RESPONSE, 
			description = CODE_200_INFO_UPDATE_RESPONSE_DESC,
			content = { @Content(mediaType = APPLICATION_JSON_MEDIA_TYPE, 
			examples = @ExampleObject(value = OBJECT_EXAMPLE_CREATE_SESSION_API_RESP), 
			schema = @Schema(implementation = SessionType.class)) }) },
			requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(mediaType = APPLICATION_JSON_MEDIA_TYPE, 
			examples = @ExampleObject(value = OBJECT_EXAMPLE_CREATE_SESSION_API_REQ), 
			schema = @Schema(implementation = SessionType.class))))
	@PutMapping(FIND_SESSION_BY_ID_API_PATH)
	@PreAuthorize(SESSION_API_AUTH)
	public SessionType update(@RequestBody SessionType request,	@PathVariable String sessionId) {
		return sessionService.update(sessionId, request);
	}

	@Operation(summary = DELETE_SESSION_API, 
			description = DELETE_SESSION_API_DESC, 
			responses = { @ApiResponse(responseCode = CODE_200_INFO_RESPONSE, 
			description = CODE_200_INFO_DELETE_RESPONSE_DESC,
			content = { @Content(mediaType = APPLICATION_JSON_MEDIA_TYPE, 
			examples = @ExampleObject(value = OBJECT_EXAMPLE_DELETE_SESSION_API_RESP), 
			schema = @Schema(implementation = GenericResponseType.class)) }) })
	@DeleteMapping(FIND_SESSION_BY_ID_API_PATH)
	@PreAuthorize(SESSION_API_AUTH)
	public GenericResponseType<?> delete(@PathVariable String sessionId) {
		return sessionService.delete(sessionId);
	}

	@Operation(summary = TOGGLE_FAVORITE_SESSION_API, 
			description = TOGGLE_FAVORITE_SESSION_API_DESC, 
			responses = { @ApiResponse(responseCode = CODE_200_INFO_RESPONSE, 
			description = CODE_200_INFO_UPDATE_RESPONSE_DESC,
			content = { @Content(mediaType = APPLICATION_JSON_MEDIA_TYPE, 
			examples = @ExampleObject(value = OBJECT_EXAMPLE_CREATE_SESSION_API_RESP), 
			schema = @Schema(implementation = SessionType.class)) }) })
	@PatchMapping(TOGGLE_FAVORITE_SESSION_API_PATH)
	@PreAuthorize(SESSION_API_AUTH)
	public SessionType toggleFavorite(@PathVariable String sessionId) {
		return sessionService.toggleFavorite(sessionId);
	}
}
