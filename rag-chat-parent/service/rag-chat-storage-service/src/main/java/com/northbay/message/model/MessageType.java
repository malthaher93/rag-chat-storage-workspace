package com.northbay.message.model;

import static com.northbay.constants.GlobalConstants.FIELD_IS_MANDATORY;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.northbay.message.enums.SenderCategoryType;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MessageType implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4385038187918517212L;
	
	@JsonProperty("Message_Sender")
	@Schema(name = "Message_Sender", description = "Sender of the message user | assistant", required = true, type = "SenderCategoryType", readOnly = true)
	@NotNull(message = FIELD_IS_MANDATORY)
	private SenderCategoryType sender;

	@JsonProperty("Message_Content")
	@Schema(name = "Message_Content", description = "A content of message", required = true, type = "String", readOnly = true)
	@NotBlank(message = FIELD_IS_MANDATORY)
	private String content;
	
	@JsonProperty("Created_At")
	@Schema(name = "Created_At", description = "Create date of message", required = true, type = "Date", readOnly = true)
	protected Date createdAt;

}
