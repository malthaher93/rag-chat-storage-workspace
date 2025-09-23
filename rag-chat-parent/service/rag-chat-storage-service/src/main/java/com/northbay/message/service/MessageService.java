package com.northbay.message.service;

import static com.northbay.constants.GlobalConstants.FIELD_IS_MANDATORY;
import static com.northbay.constants.GlobalConstants.FIELD_IS_MIN_1;
import static com.northbay.session.util.SessionHelper.getPageRequest;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.northbay.message.entity.Message;
import com.northbay.message.mapper.MessageMapper;
import com.northbay.message.model.MessageType;
import com.northbay.message.repository.MessageRepository;
import com.northbay.session.entity.Session;
import com.northbay.session.service.SessionService;

import lombok.RequiredArgsConstructor;
@Service
@RequiredArgsConstructor
@Validated
public class MessageService {

	private final SessionService sessionService;
	private final MessageRepository messageRepository;
	private final MessageMapper messageMapper;

	/***
	 * create a new message
	 * @param request
	 */
	public MessageType create(@Valid @NotNull(message = FIELD_IS_MANDATORY) MessageType request, 
			@NotBlank(message = FIELD_IS_MANDATORY) String sessionId) {
		return toModel(save(toEntity(request, getSession(sessionId))));
	}

	/***
	 * find all sessions related to the logged in user
	 * @param user
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	public Page<MessageType> findAll(@NotBlank(message = FIELD_IS_MANDATORY) String sessionId, 
			@Min(value = 1, message = FIELD_IS_MIN_1) Integer pageIndex, 
			@Min(value = 1, message = FIELD_IS_MIN_1) Integer pageSize) {
		return toModelList(findAllBySession(getPageRequest(pageIndex, pageSize), findBySessionId(sessionId)));
	}
	
	
	/***
	 * mapping from entity to dto
	 * @param message
	 * @return
	 */
	private MessageType toModel(Message message) {
		return messageMapper.toModel(message);
	}

	/***
	 * mapping from dto to entity
	 * @param request
	 * @return
	 */
	private Message toEntity(MessageType request, Session session) {
		return messageMapper.toEntity(request, session);
	}

	/***
	 * get session by id and logged in user
	 * @param user
	 * @param sessionId
	 * @return
	 */
	private Session getSession(String sessionId) {
		return sessionService.findBySessionId(sessionId);
	}
	
	/***
	 * save message into database [create]
	 * @param message
	 */
	private Message save(Message message) {
		return messageRepository.save(message);
	}

	/***
	 * find all by session
	 * @param pageableRequest
	 * @param session
	 * @return
	 */
	private Page<Message> findAllBySession(Pageable pageableRequest, Session session) {
		return (messageRepository.findAllBySession(session, pageableRequest));
	}

	/***
	 * find session by logged in user and session id
	 * @param sessionId
	 * @param user
	 * @return
	 */
	private Session findBySessionId(String sessionId) {
		return sessionService.findBySessionId(sessionId);
	}
	
	/***
	 * mapping entity instance list into model instance list
	 * @param sessions
	 * @return
	 */
	private Page<MessageType> toModelList(Page<Message> messages) {
		return messages.map(messageMapper::toModel);
	}
}
