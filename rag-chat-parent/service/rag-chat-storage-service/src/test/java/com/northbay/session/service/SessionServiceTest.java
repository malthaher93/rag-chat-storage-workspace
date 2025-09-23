package com.northbay.session.service;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import javax.validation.ConstraintViolationException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.validation.ValidationAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import com.fasterxml.jackson.core.type.TypeReference;
import com.northbay.configuration.ObjectMapperConfig;
import com.northbay.exception.ResourceNotFoundException;
import com.northbay.model.GenericResponseType;
import com.northbay.service.CachingService;
import com.northbay.session.entity.Session;
import com.northbay.session.mapper.SessionMapper;
import com.northbay.session.model.SessionType;
import com.northbay.session.repository.SessionRepository;
import com.northbay.session.util.SessionHelper;
import com.northbay.util.JsonFileReaderUtil;

@SpringBootTest
@ActiveProfiles("test")
@ContextConfiguration(classes = {ValidationAutoConfiguration.class, CachingService.class, SessionService.class, JsonFileReaderUtil.class, ObjectMapperConfig.class})
@EnableAspectJAutoProxy(proxyTargetClass = true)
class SessionServiceTest{

	@Autowired
	SessionService underTest;
	@MockBean
	SessionMapper sessionMapper;
	@MockBean
	SessionRepository sessionRepository;
	@Autowired
	JsonFileReaderUtil jsonFileReaderUtil;
	@MockBean
	CachingService cachingService;

	/***
	 * test create method 
	 * success case
	 * @throws Exception
	 */
	@Test
	void testCreate() throws Exception {
		// given
		SessionType sessoinModel = (SessionType) jsonFileReaderUtil.loadTestData("src/test/resources/mock/session/model-data.json",SessionType.class);		
		Session session = (Session) jsonFileReaderUtil.loadTestData("src/test/resources/mock/session/entity-data.json",Session.class);

		// when
		when(sessionMapper.toEntity(sessoinModel)).thenReturn(session);
		when(sessionRepository.save(session)).thenReturn(session);
		when(sessionMapper.toModel(session)).thenReturn(sessoinModel);
		SessionType actual=underTest.create(sessoinModel);

		// then
		assertNotNull(actual);
	}

	/***
	 * test create method
	 * api user is null
	 * @throws Exception
	 */
	@Test
	void testCreateWithInvalidAuthenticatedUserCase() throws Exception {

		// given
		SessionType sessoinModel = (SessionType) jsonFileReaderUtil.loadTestData("src/test/resources/mock/session/model-data.json",SessionType.class);		
		sessoinModel.setUserId(null);
		Session session = (Session) jsonFileReaderUtil.loadTestData("src/test/resources/mock/session/entity-data.json",Session.class);

		// when
		when(sessionMapper.toEntity(sessoinModel)).thenReturn(session);
		when(sessionRepository.save(session)).thenReturn(session);
		when(sessionMapper.toModel(session)).thenReturn(sessoinModel);

		// then
		assertThrows(ConstraintViolationException.class, () -> underTest.create(sessoinModel));
	}

	/***
	 * test create method
	 * session request model is null
	 * @throws Exception
	 */
	@Test
	void testCreateWithInvalidRequestCase() throws Exception {

		// given
		SessionType sessoinModel = null;		
		Session session = (Session) jsonFileReaderUtil.loadTestData("src/test/resources/mock/session/entity-data.json",Session.class);

		// when
		when(sessionMapper.toEntity(sessoinModel)).thenReturn(session);
		when(sessionRepository.save(session)).thenReturn(session);
		when(sessionMapper.toModel(session)).thenReturn(sessoinModel);

		// then
		assertThrows(ConstraintViolationException.class, () -> underTest.create( sessoinModel));
	}

	/***
	 * test create method
	 * session title is null
	 * @throws Exception
	 */
	@Test
	void testCreateWithInvalidSessionTitleCase() throws Exception {

		// given
		SessionType sessoinModel = (SessionType) jsonFileReaderUtil.loadTestData("src/test/resources/mock/session/model-data.json",SessionType.class);		
		sessoinModel.setTitle(null);
		Session session = (Session) jsonFileReaderUtil.loadTestData("src/test/resources/mock/session/entity-data.json",Session.class);

		// when
		when(sessionMapper.toEntity(sessoinModel)).thenReturn(session);
		when(sessionRepository.save(session)).thenReturn(session);
		when(sessionMapper.toModel(session)).thenReturn(sessoinModel);

		// then
		assertThrows(ConstraintViolationException.class, () -> underTest.create(sessoinModel));
	}


	/***
	 * test find all 
	 * success case
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Test
	void testFindAll() throws Exception {

		// given
		Integer pageIndex = 1;
		Integer pageSize = 10;

		List<Session> sessions = (List<Session>) jsonFileReaderUtil.loadTestData("src/test/resources/mock/session/entity-data-list.json", new TypeReference<List<Session>>() {});
		Page<Session> sessionpages = new PageImpl<>(sessions, SessionHelper.getPageRequest(pageIndex, pageSize), sessions.size());
		List<SessionType> sessionTypes = (List<SessionType>) jsonFileReaderUtil.loadTestData("src/test/resources/mock/session/model-data-list.json", new TypeReference<List<SessionType>>() {});
		SessionType sessionModel =  sessionTypes.stream().findFirst().orElse(null);		
		Session session = sessions.stream().findFirst().orElse(null);

		// when
		when(sessionRepository.findAll(SessionHelper.getPageRequest(pageIndex, pageSize))).thenReturn(sessionpages);
		when(sessionMapper.toModel(session)).thenReturn(sessionModel);

		Page<SessionType> actual=underTest.findAll(pageIndex, pageSize);

		// then
		assertNotNull(actual);
		assertEquals(false, actual.isEmpty());
	}


	/***
	 * test find all 
	 * page index = -1
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Test
	void testFindAllWithInvalidPageIndexCase() throws Exception {

		// given
		Integer pageIndex = -1;
		Integer pageSize = 10;

		List<Session> sessions = (List<Session>) jsonFileReaderUtil.loadTestData("src/test/resources/mock/session/entity-data-list.json", new TypeReference<List<Session>>() {});
		List<SessionType> sessionTypes = (List<SessionType>) jsonFileReaderUtil.loadTestData("src/test/resources/mock/session/model-data-list.json", new TypeReference<List<SessionType>>() {});
		SessionType sessionModel =  sessionTypes.stream().findFirst().orElse(null);		
		Session session = sessions.stream().findFirst().orElse(null);

		// when
		when(sessionMapper.toModel(session)).thenReturn(sessionModel);

		// then
		assertThrows(ConstraintViolationException.class, () -> underTest.findAll(pageIndex, pageSize));
	}

	/***
	 * test find all 
	 * page size = -1
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Test
	void testFindAllWithInvalidPageSizeCase() throws Exception {

		// given
		Integer pageIndex = 1;
		Integer pageSize = -1;

		List<Session> sessions = (List<Session>) jsonFileReaderUtil.loadTestData("src/test/resources/mock/session/entity-data-list.json", new TypeReference<List<Session>>() {});
		List<SessionType> sessionTypes = (List<SessionType>) jsonFileReaderUtil.loadTestData("src/test/resources/mock/session/model-data-list.json", new TypeReference<List<SessionType>>() {});
		SessionType sessionModel =  sessionTypes.stream().findFirst().orElse(null);		
		Session session = sessions.stream().findFirst().orElse(null);

		// when
		when(sessionMapper.toModel(session)).thenReturn(sessionModel);

		// then
		assertThrows(ConstraintViolationException.class, () -> underTest.findAll(pageIndex, pageSize));
	}


	/***
	 * test find by id 
	 * success case
	 * @throws Exception
	 */
	@Test
	void testFindById() throws Exception {

		// given
		Session session = (Session) jsonFileReaderUtil.loadTestData("src/test/resources/mock/session/entity-data.json", Session.class);
		String sessionId = session.getSessionId();
		Optional<Session> sessionOptional = Optional.of(session);
		SessionType sessionModel =  (SessionType) jsonFileReaderUtil.loadTestData("src/test/resources/mock/session/model-data.json", SessionType.class);

		// when
		when(sessionRepository.findBySessionId(sessionId)).thenReturn(sessionOptional);
		when(sessionMapper.toModel(session)).thenReturn(sessionModel);

		SessionType actual=underTest.findById(sessionId);

		// then
		assertNotNull(actual);
	}


	/***
	 * test find by id 
	 * session id is null
	 * @throws Exception
	 */
	@Test
	void testFindByIdWithInvalidSessionIdCase() throws Exception {

		// given
		Session session = null;
		String sessionId = null;
		SessionType sessionModel =  (SessionType) jsonFileReaderUtil.loadTestData("src/test/resources/mock/session/model-data.json", SessionType.class);

		// when
		when(sessionMapper.toModel(session)).thenReturn(sessionModel);

		// then
		assertThrows(ConstraintViolationException.class, () -> underTest.findById(sessionId));
	}

	/***
	 * test find by id 
	 * invalid session id 
	 * @throws Exception
	 */
	@Test
	void testFindByIdWithNotExistSessionIdCase() throws Exception {

		// given
		Session session = (Session) jsonFileReaderUtil.loadTestData("src/test/resources/mock/session/entity-data.json", Session.class);
		String sessionId = session.getSessionId();
		SessionType sessionModel =  (SessionType) jsonFileReaderUtil.loadTestData("src/test/resources/mock/session/model-data.json", SessionType.class);

		// when
		when(sessionRepository.findBySessionId(sessionId)).thenThrow(new ResourceNotFoundException(""));
		when(sessionMapper.toModel(session)).thenReturn(sessionModel);

		// then
		assertThrows(ResourceNotFoundException.class, () -> underTest.findById( sessionId));
	}

	/***
	 * test update method 
	 * success case
	 * @throws Exception
	 */
	@Test
	void testUpdate() throws Exception {
		// given
		SessionType sessoinModel = (SessionType) jsonFileReaderUtil.loadTestData("src/test/resources/mock/session/model-data.json",SessionType.class);		
		Session session = (Session) jsonFileReaderUtil.loadTestData("src/test/resources/mock/session/entity-data.json",Session.class);
		String sessionId = session.getSessionId();
		Optional<Session> sessionOptional = Optional.of(session);

		// when
		when(sessionRepository.findBySessionId(sessionId)).thenReturn(sessionOptional);
		when(sessionRepository.save(session)).thenReturn(session);
		when(sessionMapper.toModel(session)).thenReturn(sessoinModel);
		SessionType actual=underTest.update(sessionId, sessoinModel);

		// then
		assertNotNull(actual);
	}


	/***
	 * test update method 
	 * session id is null
	 * @throws Exception
	 */
	@Test
	void testUpdateWithInvalidSessionIdCase() throws Exception {
		// given
		SessionType sessoinModel = (SessionType) jsonFileReaderUtil.loadTestData("src/test/resources/mock/session/model-data.json",SessionType.class);		
		Session session = null;
		String sessionId = null;
		Optional<Session> sessionOptional = null;

		// when
		when(sessionRepository.findBySessionId(sessionId)).thenReturn(sessionOptional);
		when(sessionRepository.save(session)).thenReturn(session);
		when(sessionMapper.toModel(session)).thenReturn(sessoinModel);

		// then
		assertThrows(ConstraintViolationException.class, () -> underTest.update(sessionId, sessoinModel));
	}

	/***
	 * test update method 
	 * session id is not exist
	 * @throws Exception
	 */
	@Test
	void testUpdateWithNotExistSessionIdCase() throws Exception {
		// given
		SessionType sessoinModel = (SessionType) jsonFileReaderUtil.loadTestData("src/test/resources/mock/session/model-data.json",SessionType.class);		
		Session session = (Session) jsonFileReaderUtil.loadTestData("src/test/resources/mock/session/entity-data.json",Session.class);
		String sessionId = session.getSessionId();

		// when
		when(sessionRepository.findBySessionId(sessionId)).thenThrow(new ResourceNotFoundException(sessionId));
		when(sessionRepository.save(session)).thenReturn(session);
		when(sessionMapper.toModel(session)).thenReturn(sessoinModel);

		// then
		assertThrows(ResourceNotFoundException.class, () -> underTest.update(sessionId, sessoinModel));
	}


	/***
	 * test update method 
	 * session request model is null
	 * @throws Exception
	 */
	@Test
	void testUpdateWithInvalidSessionRequestCase() throws Exception {
		// given
		SessionType sessoinModel = null;		
		Session session = (Session) jsonFileReaderUtil.loadTestData("src/test/resources/mock/session/entity-data.json",Session.class);
		String sessionId = session.getSessionId();
		Optional<Session> sessionOptional = Optional.of(session);

		// when
		when(sessionRepository.findBySessionId(sessionId)).thenReturn(sessionOptional);
		when(sessionRepository.save(session)).thenReturn(session);
		when(sessionMapper.toModel(session)).thenReturn(sessoinModel);

		// then
		assertThrows(ConstraintViolationException.class, () -> underTest.update(sessionId, sessoinModel));
	}


	/***
	 * test update method 
	 * session title model is null
	 * @throws Exception
	 */
	@Test
	void testUpdateWithInvalidSessionTitleCase() throws Exception {
		// given
		SessionType sessoinModel = (SessionType) jsonFileReaderUtil.loadTestData("src/test/resources/mock/session/model-data.json",SessionType.class);		
		sessoinModel.setTitle(null);
		Session session = (Session) jsonFileReaderUtil.loadTestData("src/test/resources/mock/session/entity-data.json",Session.class);
		String sessionId = session.getSessionId();
		Optional<Session> sessionOptional = Optional.of(session);

		// when
		when(sessionRepository.findBySessionId(sessionId)).thenReturn(sessionOptional);
		when(sessionRepository.save(session)).thenReturn(session);
		when(sessionMapper.toModel(session)).thenReturn(sessoinModel);

		// then
		assertThrows(ConstraintViolationException.class, () -> underTest.update(sessionId, sessoinModel));
	}


	/***
	 * test toggleFavorite method 
	 * success case
	 * @throws Exception
	 */
	@Test
	void testToggleFavorite() throws Exception {
		// given
		SessionType sessoinModel = (SessionType) jsonFileReaderUtil.loadTestData("src/test/resources/mock/session/model-data.json",SessionType.class);		
		Session session = (Session) jsonFileReaderUtil.loadTestData("src/test/resources/mock/session/entity-data.json",Session.class);
		String sessionId = session.getSessionId();
		Optional<Session> sessionOptional = Optional.of(session);

		// when
		when(sessionRepository.findBySessionId(sessionId)).thenReturn(sessionOptional);
		when(sessionRepository.save(session)).thenReturn(session);
		when(sessionMapper.toModel(session)).thenReturn(sessoinModel);
		SessionType actual=underTest.toggleFavorite(sessionId);

		// then
		assertNotNull(actual);
	}

	/***
	 * test toggleFavorite method 
	 * session id is null
	 * @throws Exception
	 */
	@Test
	void testToggleFavoriteWithInvalidSessionIdCase() throws Exception {
		// given
		SessionType sessoinModel = (SessionType) jsonFileReaderUtil.loadTestData("src/test/resources/mock/session/model-data.json",SessionType.class);		
		Session session = null;
		String sessionId = null;
		Optional<Session> sessionOptional = null;

		// when
		when(sessionRepository.findBySessionId(sessionId)).thenReturn(sessionOptional);
		when(sessionRepository.save(session)).thenReturn(session);
		when(sessionMapper.toModel(session)).thenReturn(sessoinModel);

		// then
		assertThrows(ConstraintViolationException.class, () -> underTest.toggleFavorite(sessionId));
	}

	/***
	 * test toggleFavorite method 
	 * session id is not exist
	 * @throws Exception
	 */
	@Test
	void testToggleFavoriteWithNotExistSessionIdCase() throws Exception {
		// given
		SessionType sessoinModel = (SessionType) jsonFileReaderUtil.loadTestData("src/test/resources/mock/session/model-data.json",SessionType.class);		
		Session session = (Session) jsonFileReaderUtil.loadTestData("src/test/resources/mock/session/entity-data.json",Session.class);
		String sessionId = session.getSessionId();

		// when
		when(sessionRepository.findBySessionId(sessionId)).thenThrow(new ResourceNotFoundException(sessionId));
		when(sessionRepository.save(session)).thenReturn(session);
		when(sessionMapper.toModel(session)).thenReturn(sessoinModel);

		// then
		assertThrows(ResourceNotFoundException.class, () -> underTest.toggleFavorite(sessionId));
	}


	/***
	 * test delete method 
	 * success case
	 * @throws Exception
	 */
	@Test
	void testDelete() throws Exception {
		// given
		SessionType sessoinModel = (SessionType) jsonFileReaderUtil.loadTestData("src/test/resources/mock/session/model-data.json",SessionType.class);		
		Session session = (Session) jsonFileReaderUtil.loadTestData("src/test/resources/mock/session/entity-data.json",Session.class);
		String sessionId = session.getSessionId();
		Optional<Session> sessionOptional = Optional.of(session);

		// when
		when(sessionRepository.findBySessionId(sessionId)).thenReturn(sessionOptional);
		doNothing().when(sessionRepository).delete(session);
		when(sessionMapper.toModel(session)).thenReturn(sessoinModel);
		GenericResponseType<?> actual=underTest.delete( sessionId);

		// then
		assertNotNull(actual);
	}

	/***
	 * test delete method 
	 * session id is null
	 * @throws Exception
	 */
	@Test
	void testDeleteWithInvalidSessionIdCase() throws Exception {
		// given
		SessionType sessoinModel = (SessionType) jsonFileReaderUtil.loadTestData("src/test/resources/mock/session/model-data.json",SessionType.class);		
		Session session = null;
		String sessionId = null;
		Optional<Session> sessionOptional = null;

		// when
		when(sessionRepository.findBySessionId(sessionId)).thenReturn(sessionOptional);
		doNothing().when(sessionRepository).delete(session);
		when(sessionMapper.toModel(session)).thenReturn(sessoinModel);

		// then
		assertThrows(ConstraintViolationException.class, () -> underTest.delete( sessionId));
	}
	
	
	/***
	 * test delete method 
	 * session id is not exist
	 * @throws Exception
	 */
	@Test
	void testDeleteWithNotExistSessionIdCase() throws Exception {
		// given
		SessionType sessoinModel = (SessionType) jsonFileReaderUtil.loadTestData("src/test/resources/mock/session/model-data.json",SessionType.class);		
		Session session = (Session) jsonFileReaderUtil.loadTestData("src/test/resources/mock/session/entity-data.json",Session.class);
		String sessionId = session.getSessionId();

		// when
		when(sessionRepository.findBySessionId(sessionId)).thenThrow(new ResourceNotFoundException(sessionId));
		doNothing().when(sessionRepository).delete(session);
		when(sessionMapper.toModel(session)).thenReturn(sessoinModel);

		// then
		assertThrows(ResourceNotFoundException.class, () -> underTest.delete(sessionId));
	}

}