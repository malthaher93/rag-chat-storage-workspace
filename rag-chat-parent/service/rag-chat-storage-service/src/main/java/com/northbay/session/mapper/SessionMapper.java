package com.northbay.session.mapper;
import static org.mapstruct.NullValueCheckStrategy.ALWAYS;
import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.northbay.common.mapper.DataTypeMapper;
import com.northbay.session.entity.Session;
import com.northbay.session.model.SessionType;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = IGNORE, nullValueCheckStrategy = ALWAYS, builder = @org.mapstruct.Builder(disableBuilder = true)
//,uses = MessageMapper.class
)
public interface SessionMapper extends DataTypeMapper<Session, SessionType> {

	@Override
	@Mapping(target = "sessionId", source = "id")
	@Mapping(target = "messageList", ignore = true)
	Session toEntity(SessionType dto);
	
	@Mapping(target = "id", source = "sessionId")
	SessionType toModel(Session entity);

}
