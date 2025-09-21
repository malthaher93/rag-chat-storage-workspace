package com.northbay.message.constant;

import lombok.Value;

@Value
public class MessageConstants {

	private MessageConstants() {
		throw new IllegalStateException("Utility class");
	}
	public static final String CREATE_MESSAGE_API_PATH = "{sessionId}/message";

	public static final String MESSAGE_CONTROLLER_DESC = "A controller used to manage message operation";

	public static final String MESSAGE_CONTROLLER = "Message Controller";

	public static final String OBJECT_EXAMPLE_CREATE_MESSAGE_API_RESP = "{\"Message_Sender\" : \"USER\", \"Message_Content\" : \"Hello\" }";
	
	public static final String OBJECT_EXAMPLE_CREATE_MESSAGE_API_REQ = "{\"Message_Sender\" : \"USER\", \"Message_Content\" : \"Hello\" }";

	public static final String CODE_200_INFO_RETRIEVE_RESPONSE_DESC = "Successfully retrieved";

	public static final String FIND_ALL_MESSAGES_API_DESC = "An API used to find all messages b for authenticated user from database";

	public static final String FIND_ALL_MESSAGES_API = "Find all messages API";

	public static final String SESSION_API_AUTH = "hasAnyAuthority('ADMIN', 'USER')";

	public static final String CODE_201_INFO_RESPONSE_DESC = "Successfully created";

	public static final String CREATE_MESSAGE_API_DESC = "An API used to create a message and persist it into database";

	public static final String CREATE_MESSAGE_API = "Create message API";

}
