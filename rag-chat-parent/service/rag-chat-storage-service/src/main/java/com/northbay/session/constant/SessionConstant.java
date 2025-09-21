package com.northbay.session.constant;

import lombok.Value;

@Value
public class SessionConstant {
	
	private SessionConstant() {
		throw new IllegalStateException("Utility class");
	}

	public static final String SESSION_CONTROLLER_DESC = "A controller used to manage session operation";

	public static final String SESSION_CONTROLLER = "Session Controller";

	public static final String OBJECT_EXAMPLE_CREATE_SESSION_API_RESP = "{\"Session_Id\" : \"XXX-XXX-XXX-XXX\", \"Session_Title\" : \"Test\", \"Favorite\" : false}";
	
	public static final String OBJECT_EXAMPLE_DELETE_SESSION_API_RESP = "{\"status\": \"SUCCESS\",\"httpStatus\": \"OK\", \"infoDescription\": \"Sucessfully deleted\",\"responseDateTime\": \"2025-09-20 18:13:03\"	}";
	
	public static final String OBJECT_EXAMPLE_CREATE_SESSION_API_REQ = "{\"Session_Title\" : \"Test\"}";
	
	public static final String TOGGLE_FAVORITE_SESSION_API_PATH = "{sessionId}/favorite";

	public static final String TOGGLE_FAVORITE_SESSION_API_DESC = "An API used to toggle session favorite and persist it into database";

	public static final String TOGGLE_FAVORITE_SESSION_API = "Toggle favorite session API";

	public static final String CODE_200_INFO_DELETE_RESPONSE_DESC = "Successfully deleted";

	public static final String DELETE_SESSION_API_DESC = "An API used to delete session from database";

	public static final String DELETE_SESSION_API = "Delete session API";

	public static final String CODE_200_INFO_UPDATE_RESPONSE_DESC = "Successfully updated";

	public static final String UPDATE_SESSION_API_DESC = "An API used to update session title and persist it into database";

	public static final String UPDATE_SESSION_API = "Update session API";

	public static final String FIND_SESSION_BY_ID_API_PATH = "{sessionId}";

	public static final String FIND_SESSION_BY_ID_API_DESC = "An API used to find session by ID for authenticated user from database";

	public static final String FIND_SESSION_BY_ID_API = "Find session by ID API";

	public static final String CODE_200_INFO_RETRIEVE_RESPONSE_DESC = "Successfully retrieved";

	public static final String FIND_ALL_SESSIONS_API_DESC = "An API used to find all related sessions for authenticated user from database";

	public static final String FIND_ALL_SESSIONS_API = "Find all sessions API";

	public static final String SESSION_API_AUTH = "hasAnyAuthority('ADMIN', 'USER')";

	public static final String CODE_201_INFO_RESPONSE_DESC = "Successfully created";

	public static final String CREATE_SESSION_API_DESC = "An API used to create a session and persist it into database";

	public static final String CREATE_SESSION_API = "Create session API";

	public static final String SUCESSFULLY_DELETED = "Sucessfully deleted";
	
	public static final String INVALID_API_USER = "invalid api user";
	
	public static final String REQUESTED_RESOURCE_NOT_FOUND = "Requested resource not found";
	
}
