package com.northbay.message.mapper;
import static org.mapstruct.NullValueCheckStrategy.ALWAYS;
import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.northbay.common.mapper.DataTypeMapper;
import com.northbay.message.entity.Message;
import com.northbay.message.model.MessageType;
import com.northbay.session.entity.Session;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = IGNORE, nullValueCheckStrategy = ALWAYS, builder = @org.mapstruct.Builder(disableBuilder = true))
public interface MessageMapper extends DataTypeMapper<Message, MessageType> {

	@Override
	@Mapping(target = "updatedAt", ignore = true)
	@Mapping(target = "id", ignore = true)
	@Mapping(target = "session", ignore = true)
	Message toEntity(MessageType dto);

	default Message toEntity(MessageType dto, Session session) {
		Message message =  toEntity(dto);
		message.setSession(session);
		return message;
	}
}
