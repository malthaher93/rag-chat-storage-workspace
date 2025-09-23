package com.northbay.message.controller;

import static com.northbay.constants.GlobalConstants.APPLICATION_JSON_MEDIA_TYPE;
import static com.northbay.constants.GlobalConstants.CODE_200_INFO_RESPONSE;
import static com.northbay.constants.GlobalConstants.CODE_201_INFO_RESPONSE;
import static com.northbay.message.constant.MessageConstants.CODE_200_INFO_RETRIEVE_RESPONSE_DESC;
import static com.northbay.message.constant.MessageConstants.CODE_201_INFO_RESPONSE_DESC;
import static com.northbay.message.constant.MessageConstants.CREATE_MESSAGE_API;
import static com.northbay.message.constant.MessageConstants.CREATE_MESSAGE_API_PATH;
import static com.northbay.message.constant.MessageConstants.FIND_ALL_MESSAGES_API;
import static com.northbay.message.constant.MessageConstants.FIND_ALL_MESSAGES_API_DESC;
import static com.northbay.message.constant.MessageConstants.MESSAGE_CONTROLLER;
import static com.northbay.message.constant.MessageConstants.MESSAGE_CONTROLLER_DESC;
import static com.northbay.message.constant.MessageConstants.OBJECT_EXAMPLE_CREATE_MESSAGE_API_REQ;
import static com.northbay.message.constant.MessageConstants.OBJECT_EXAMPLE_CREATE_MESSAGE_API_RESP;
import static com.northbay.message.constant.MessageConstants.SESSION_API_AUTH;

import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.northbay.common.controller.AbstractChatController;
import com.northbay.message.model.MessageType;
import com.northbay.message.service.MessageService;
import com.northbay.session.model.SessionType;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@Tag(name = MESSAGE_CONTROLLER, description = MESSAGE_CONTROLLER_DESC)
@RequiredArgsConstructor
public class MessageController extends AbstractChatController {

	private final MessageService messageService;
	
	@Operation(summary = CREATE_MESSAGE_API, 
			description = CREATE_MESSAGE_API, 
			responses = { @ApiResponse(responseCode = CODE_201_INFO_RESPONSE, 
			description = CODE_201_INFO_RESPONSE_DESC,
			content = { @Content(mediaType = APPLICATION_JSON_MEDIA_TYPE, 
			examples = @ExampleObject(value = OBJECT_EXAMPLE_CREATE_MESSAGE_API_RESP), 
			schema = @Schema(implementation = MessageType.class)) }) },
			requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(mediaType = APPLICATION_JSON_MEDIA_TYPE, 
			examples = @ExampleObject(value = OBJECT_EXAMPLE_CREATE_MESSAGE_API_REQ), 
			schema = @Schema(implementation = SessionType.class))))
	@PostMapping(CREATE_MESSAGE_API_PATH)
	@PreAuthorize(SESSION_API_AUTH)
	public MessageType create(@RequestBody MessageType request,
			@PathVariable String sessionId) {
		return messageService.create(request, sessionId);
	}

	@Operation(summary = FIND_ALL_MESSAGES_API, 
			description = FIND_ALL_MESSAGES_API_DESC, 
			responses = { @ApiResponse(responseCode = CODE_200_INFO_RESPONSE, 
			description = CODE_200_INFO_RETRIEVE_RESPONSE_DESC, 
			content = { @Content(mediaType = APPLICATION_JSON_MEDIA_TYPE, 
			schema = @Schema(implementation = MessageType.class)) }) })
	@GetMapping(CREATE_MESSAGE_API_PATH)
	@PreAuthorize(SESSION_API_AUTH)
	public Page<MessageType> findAll(@RequestParam(required = false, defaultValue = "1") Integer pageIndex, 
			@RequestParam(required = false, defaultValue = "10") Integer pageSize,	
			@PathVariable String sessionId) {
		return messageService.findAll(sessionId,pageIndex, pageSize);
	}
}
