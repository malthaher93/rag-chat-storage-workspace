package com.northbay.session.model;

import static com.northbay.constants.GlobalConstants.FIELD_IS_MANDATORY;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SessionType implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4385038187918517212L;

	@JsonProperty("Session_Id")
	@Schema(name = "Session_Id", description = "Id of the session", required = true, type = "String", readOnly = true)
	private String id;

	@JsonProperty("Session_Title")
	@Schema(name = "Session_Title", description = "Title of the session", required = true, type = "String", readOnly = true)
	@NotBlank(message = FIELD_IS_MANDATORY)
	private String title;

	@JsonProperty("Favorite")
	@Schema(name = "Favorite", description = "A flag indicate to the session is favorite or not", required = true, type = "Boolean", readOnly = true)
	private Boolean favorite;
	
	@JsonProperty("Created_At")
	@Schema(name = "Created_At", description = "Create date of session", required = true, type = "Date", readOnly = true)
	protected Date createdAt;

	@JsonProperty("Updated_At")
	@Schema(name = "Updated_At", description = "Last update date of the session", required = true, type = "Date", readOnly = true)
	protected Date updatedAt;


	/*
	@JsonProperty("Messges")
	@Schema(name = "Messages", description = "List of message related to the session", required = true, type = "MessageType", readOnly = true)
	private Collection<MessageType> messageList;
	*/
}
