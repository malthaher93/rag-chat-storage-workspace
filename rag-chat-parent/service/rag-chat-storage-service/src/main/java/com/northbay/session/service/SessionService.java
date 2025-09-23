package com.northbay.session.service;

import static com.northbay.constants.GlobalConstants.FIELD_IS_MANDATORY;
import static com.northbay.constants.GlobalConstants.FIELD_IS_MIN_1;
import static com.northbay.session.constant.SessionConstant.REQUESTED_RESOURCE_NOT_FOUND;
import static com.northbay.session.constant.SessionConstant.SUCESSFULLY_DELETED;
import static com.northbay.session.util.SessionHelper.getPageRequest;

import java.util.UUID;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.northbay.exception.ResourceNotFoundException;
import com.northbay.model.GenericResponseType;
import com.northbay.service.CachingService;
import com.northbay.session.entity.Session;
import com.northbay.session.mapper.SessionMapper;
import com.northbay.session.model.SessionType;
import com.northbay.session.repository.SessionRepository;
import com.northbay.util.ResponseUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Validated
public class SessionService {

	private final SessionRepository sessionRepository;
	private final SessionMapper sessionMapper;
	private final CachingService cachingService;
	
	/***
	 * create a new session
	 * @param request
	 */
	public SessionType create(@Valid @NotNull(message = FIELD_IS_MANDATORY) SessionType request) {
		return toModel(save(toEntity(request)));
	}
	
	/***
	 * find all sessions
	 * @param user
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	
	public Page<SessionType> findAll(@Min(value = 1, message = FIELD_IS_MIN_1) Integer pageIndex, 
			@Min(value = 1, message = FIELD_IS_MIN_1) Integer pageSize) {
		return toModelList(sessionRepository.findAll(getPageRequest(pageIndex, pageSize)));
	}
	
	/***
	 * find session by session id from database
	 * @param sessionId
	 * @return
	 */
	public SessionType findById(@NotBlank(message = FIELD_IS_MANDATORY) String sessionId) {
		return toModel(findBySessionId(sessionId));   		
	}
	
	/***
	 * update session title into database
	 * @param sessionId
	 * @param request
	 */
	public SessionType update(@NotBlank(message = FIELD_IS_MANDATORY) String sessionId, 
			@Valid @NotNull(message = FIELD_IS_MANDATORY) SessionType request) {
		Session session = findBySessionId(sessionId);
		session.setTitle(request.getTitle());
		return toModel(save(session));	
	}
	
	/***
	 * toggle favorite session and update it into database
	 * @param sessionId
	 */
	public SessionType toggleFavorite(@NotBlank(message = FIELD_IS_MANDATORY) String sessionId) {
		Session session = findBySessionId(sessionId);
		session.setFavorite(!session.getFavorite());
		return toModel(save(session));	
	}
	
	/***
	 * delete session from database
	 * @param sessionId
	 */
	public GenericResponseType<?> delete(@NotBlank(message = FIELD_IS_MANDATORY) String sessionId) {
		
		sessionRepository.delete(findBySessionId(sessionId));
		cachingService.evictByCacheNameAndKey("SessionDataCacheMap", sessionId);
		return ResponseUtil.getInfoGenericResponse(HttpStatus.OK, SUCESSFULLY_DELETED);
	}
	
	
	/***
	 * find session by logged in user and session id
	 * @param sessionId
	 * @return
	 */
	@Cacheable(value = "SessionDataCacheMap", unless = "#result == null")
	public Session findBySessionId(@NotBlank(message = FIELD_IS_MANDATORY) String sessionId) {
		return sessionRepository.findBySessionId(sessionId)
				.orElseThrow(() -> new ResourceNotFoundException(REQUESTED_RESOURCE_NOT_FOUND));
	}

	/***
	 * mapping dto instance into entity instance
	 * @param user
	 * @param request
	 * @return
	 */
	private Session toEntity(SessionType request) {
		Session session = sessionMapper.toEntity(request);
		session.setSessionId(UUID.randomUUID().toString());		
		return session;
	}

	/***
	 * mapping entity instance list into model instance list
	 * @param sessions
	 * @return
	 */
	private Page<SessionType> toModelList(Page<Session> sessions) {
		return sessions.map(sessionMapper::toModel);
	}

	/***
	 * mapping entity instance into model instance
	 * @param session
	 * @return
	 */
	private SessionType toModel(Session session) {
		return sessionMapper.toModel(session);
	}

	/***
	 * save session into database [create/update]
	 * @param session
	 */
	private Session save(Session session) {
		return sessionRepository.save(session);
	}

}
