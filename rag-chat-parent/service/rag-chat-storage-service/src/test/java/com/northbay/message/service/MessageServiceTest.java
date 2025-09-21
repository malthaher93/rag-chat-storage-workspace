package com.northbay.message.service;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.List;

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
import com.northbay.message.entity.Message;
import com.northbay.message.mapper.MessageMapper;
import com.northbay.message.model.MessageType;
import com.northbay.message.repository.MessageRepository;
import com.northbay.model.ApiUserType;
import com.northbay.session.entity.Session;
import com.northbay.session.service.SessionService;
import com.northbay.session.util.SessionHelper;
import com.northbay.util.JsonFileReaderUtil;

@SpringBootTest
@ActiveProfiles("test")
@ContextConfiguration(classes = {ValidationAutoConfiguration.class, MessageService.class, JsonFileReaderUtil.class, ObjectMapperConfig.class})
@EnableAspectJAutoProxy(proxyTargetClass = true)
class MessageServiceTest{

	@Autowired
	MessageService underTest;
	@MockBean
	MessageMapper messageMapper;
	@MockBean
	MessageRepository messageRepository;
	@MockBean
	SessionService sessionService;
	@Autowired
	JsonFileReaderUtil jsonFileReaderUtil;

	/***
	 * test create method 
	 * success case
	 * @throws Exception
	 */
	@Test
	void testCreate() throws Exception {
		// given
		MessageType messageModel = (MessageType) jsonFileReaderUtil.loadTestData("src/test/resources/mock/message/model-data.json",MessageType.class);		
		ApiUserType user = (ApiUserType) jsonFileReaderUtil.loadTestData("src/test/resources/mock/apiuser/api-user.json",ApiUserType.class);
		Session session = (Session) jsonFileReaderUtil.loadTestData("src/test/resources/mock/session/entity-data.json",Session.class);
		Message message = (Message) jsonFileReaderUtil.loadTestData("src/test/resources/mock/message/entity-data.json",Message.class);
		String sessionId = session.getSessionId();

		// when
		when(sessionService.findByUserIdAndSessionId(user, sessionId)).thenReturn(session);
		when(messageMapper.toEntity(messageModel, session)).thenReturn(message);
		when(messageRepository.save(message)).thenReturn(message);
		when(messageMapper.toModel(message)).thenReturn(messageModel);
		MessageType actual=underTest.create(messageModel, user, sessionId);

		// then
		assertNotNull(actual);
	}

	/***
	 * test create method
	 * sender category type is null
	 * @throws Exception
	 */
	@Test
	void testCreateWithInvalidSenderCategoryTypeCase() throws Exception {
		// given
		MessageType messageModel = (MessageType) jsonFileReaderUtil.loadTestData("src/test/resources/mock/message/model-data.json",MessageType.class);		
		messageModel.setSender(null);
		ApiUserType user = (ApiUserType) jsonFileReaderUtil.loadTestData("src/test/resources/mock/apiuser/api-user.json",ApiUserType.class);
		Session session = (Session) jsonFileReaderUtil.loadTestData("src/test/resources/mock/session/entity-data.json",Session.class);
		Message message = (Message) jsonFileReaderUtil.loadTestData("src/test/resources/mock/message/entity-data.json",Message.class);
		String sessionId = session.getSessionId();

		// when
		when(sessionService.findByUserIdAndSessionId(user, sessionId)).thenReturn(session);
		when(messageMapper.toEntity(messageModel, session)).thenReturn(message);
		when(messageRepository.save(message)).thenReturn(message);
		when(messageMapper.toModel(message)).thenReturn(messageModel);

		// then
		assertThrows(ConstraintViolationException.class, () -> underTest.create(messageModel, user, sessionId));
	}

	/***
	 * test create method
	 * message content is null
	 * @throws Exception
	 */
	@Test
	void testCreateWithInvalidMessageContentCase() throws Exception {
		// given
		MessageType messageModel = (MessageType) jsonFileReaderUtil.loadTestData("src/test/resources/mock/message/model-data.json",MessageType.class);		
		messageModel.setContent(null);
		ApiUserType user = (ApiUserType) jsonFileReaderUtil.loadTestData("src/test/resources/mock/apiuser/api-user.json",ApiUserType.class);
		Session session = (Session) jsonFileReaderUtil.loadTestData("src/test/resources/mock/session/entity-data.json",Session.class);
		Message message = (Message) jsonFileReaderUtil.loadTestData("src/test/resources/mock/message/entity-data.json",Message.class);
		String sessionId = session.getSessionId();

		// when
		when(sessionService.findByUserIdAndSessionId(user, sessionId)).thenReturn(session);
		when(messageMapper.toEntity(messageModel, session)).thenReturn(message);
		when(messageRepository.save(message)).thenReturn(message);
		when(messageMapper.toModel(message)).thenReturn(messageModel);

		// then
		assertThrows(ConstraintViolationException.class, () -> underTest.create(messageModel, user, sessionId));
	}

	/***
	 * test create method
	 * message request is null
	 * @throws Exception
	 */
	@Test
	void testCreateWithInvalidMessageRequestCase() throws Exception {

		MessageType messageModel = null;		
		ApiUserType user = (ApiUserType) jsonFileReaderUtil.loadTestData("src/test/resources/mock/apiuser/api-user.json",ApiUserType.class);
		Session session = (Session) jsonFileReaderUtil.loadTestData("src/test/resources/mock/session/entity-data.json",Session.class);
		Message message = (Message) jsonFileReaderUtil.loadTestData("src/test/resources/mock/message/entity-data.json",Message.class);
		String sessionId = session.getSessionId();

		// when
		when(sessionService.findByUserIdAndSessionId(user, sessionId)).thenReturn(session);
		when(messageMapper.toEntity(messageModel, session)).thenReturn(message);
		when(messageRepository.save(message)).thenReturn(message);
		when(messageMapper.toModel(message)).thenReturn(messageModel);

		// then
		assertThrows(ConstraintViolationException.class, () -> underTest.create(messageModel, user, sessionId));
	}

	/***
	 * test create method
	 * api user is null
	 * @throws Exception
	 */
	@Test
	void testCreateWithInvalidAuthenticatedUserCase() throws Exception {

		// given
		MessageType messageModel = (MessageType) jsonFileReaderUtil.loadTestData("src/test/resources/mock/message/model-data.json",MessageType.class);		
		ApiUserType user = null;
		Session session = (Session) jsonFileReaderUtil.loadTestData("src/test/resources/mock/session/entity-data.json",Session.class);
		Message message = (Message) jsonFileReaderUtil.loadTestData("src/test/resources/mock/message/entity-data.json",Message.class);
		String sessionId = session.getSessionId();

		// when
		when(sessionService.findByUserIdAndSessionId(user, sessionId)).thenReturn(session);
		when(messageMapper.toEntity(messageModel, session)).thenReturn(message);
		when(messageRepository.save(message)).thenReturn(message);
		when(messageMapper.toModel(message)).thenReturn(messageModel);

		// then
		assertThrows(ConstraintViolationException.class, () -> underTest.create(messageModel, user, sessionId));
	}

	/***
	 * test create method
	 * session id is null
	 * @throws Exception
	 */
	@Test
	void testCreateWithMissedSessionIdCase() throws Exception {

		// given
		MessageType messageModel = (MessageType) jsonFileReaderUtil.loadTestData("src/test/resources/mock/message/model-data.json",MessageType.class);		
		ApiUserType user = (ApiUserType) jsonFileReaderUtil.loadTestData("src/test/resources/mock/apiuser/api-user.json",ApiUserType.class);
		Session session = null;
		Message message = (Message) jsonFileReaderUtil.loadTestData("src/test/resources/mock/message/entity-data.json",Message.class);
		String sessionId = null;

		// when
		when(sessionService.findByUserIdAndSessionId(user, sessionId)).thenReturn(session);
		when(messageMapper.toEntity(messageModel, session)).thenReturn(message);
		when(messageRepository.save(message)).thenReturn(message);
		when(messageMapper.toModel(message)).thenReturn(messageModel);

		// then
		assertThrows(ConstraintViolationException.class, () -> underTest.create(messageModel, user, sessionId));
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
		ApiUserType user = (ApiUserType) jsonFileReaderUtil.loadTestData("src/test/resources/mock/apiuser/api-user.json",ApiUserType.class);
		Session session = (Session) jsonFileReaderUtil.loadTestData("src/test/resources/mock/session/entity-data.json",Session.class);
		String sessionId = session.getSessionId();

		Integer pageIndex = 1;
		Integer pageSize = 10;

		List<Message> messages = (List<Message>) jsonFileReaderUtil.loadTestData("src/test/resources/mock/message/entity-data-list.json", new TypeReference<List<Message>>() {});
		Page<Message> messagepages = new PageImpl<>(messages, SessionHelper.getPageRequest(pageIndex, pageSize), messages.size());
		List<MessageType> messageTypes = (List<MessageType>) jsonFileReaderUtil.loadTestData("src/test/resources/mock/message/model-data-list.json", new TypeReference<List<MessageType>>() {});
		MessageType messageModel =  messageTypes.stream().findFirst().orElse(null);		
		Message message = messages.stream().findFirst().orElse(null);

		// when
		when(sessionService.findByUserIdAndSessionId(user, sessionId)).thenReturn(session);
		when(messageRepository.findAllBySession(session, SessionHelper.getPageRequest(pageIndex, pageSize))).thenReturn(messagepages);
		when(messageMapper.toModel(message)).thenReturn(messageModel);

		Page<MessageType> actual=underTest.findAll(sessionId, user, pageIndex, pageSize);
		// then

		assertNotNull(actual);
		assertEquals(false, actual.isEmpty());
	}

	/***
	 * test find all
	 * session id is not exist
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Test
	void testFindAllWithInvalidSessionIdCase() throws Exception {

		// given
		ApiUserType user = (ApiUserType) jsonFileReaderUtil.loadTestData("src/test/resources/mock/apiuser/api-user.json",ApiUserType.class);
		Session session = (Session) jsonFileReaderUtil.loadTestData("src/test/resources/mock/session/entity-data.json",Session.class);
		String sessionId = session.getSessionId();

		Integer pageIndex = 1;
		Integer pageSize = 10;

		List<Message> messages = (List<Message>) jsonFileReaderUtil.loadTestData("src/test/resources/mock/message/entity-data-list.json", new TypeReference<List<Message>>() {});
		Page<Message> messagepages = new PageImpl<>(messages, SessionHelper.getPageRequest(pageIndex, pageSize), messages.size());
		List<MessageType> messageTypes = (List<MessageType>) jsonFileReaderUtil.loadTestData("src/test/resources/mock/message/model-data-list.json", new TypeReference<List<MessageType>>() {});
		MessageType messageModel =  messageTypes.stream().findFirst().orElse(null);		
		Message message = messages.stream().findFirst().orElse(null);

		// when
		when(sessionService.findByUserIdAndSessionId(user, sessionId)).thenThrow(new ResourceNotFoundException(""));
		when(messageRepository.findAllBySession(session, SessionHelper.getPageRequest(pageIndex, pageSize))).thenReturn(messagepages);
		when(messageMapper.toModel(message)).thenReturn(messageModel);

		// then
		assertThrows(ResourceNotFoundException.class, () -> underTest.findAll(sessionId, user, pageIndex, pageSize));	
	}
	
	/***
	 * test find all
	 * session id is null
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Test
	void testFindAllWithMissedSessionIdCase() throws Exception {

		// given
		ApiUserType user = (ApiUserType) jsonFileReaderUtil.loadTestData("src/test/resources/mock/apiuser/api-user.json",ApiUserType.class);
		Session session = null;
		String sessionId = null;

		Integer pageIndex = 1;
		Integer pageSize = 10;

		List<Message> messages = (List<Message>) jsonFileReaderUtil.loadTestData("src/test/resources/mock/message/entity-data-list.json", new TypeReference<List<Message>>() {});
		Page<Message> messagepages = new PageImpl<>(messages, SessionHelper.getPageRequest(pageIndex, pageSize), messages.size());
		List<MessageType> messageTypes = (List<MessageType>) jsonFileReaderUtil.loadTestData("src/test/resources/mock/message/model-data-list.json", new TypeReference<List<MessageType>>() {});
		MessageType messageModel =  messageTypes.stream().findFirst().orElse(null);		
		Message message = messages.stream().findFirst().orElse(null);

		// when
		when(sessionService.findByUserIdAndSessionId(user, sessionId)).thenReturn(session);
		when(messageRepository.findAllBySession(session, SessionHelper.getPageRequest(pageIndex, pageSize))).thenReturn(messagepages);
		when(messageMapper.toModel(message)).thenReturn(messageModel);

		// then
		assertThrows(ConstraintViolationException.class, () -> underTest.findAll(sessionId, user, pageIndex, pageSize));	
	}

	/***
	 * test find All 
	 * user is null
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Test
	void testFindAllWithInvalidAuthenticatedUserCase() throws Exception {

		// given
		ApiUserType user = null;
		Session session = (Session) jsonFileReaderUtil.loadTestData("src/test/resources/mock/session/entity-data.json",Session.class);
		String sessionId = session.getSessionId();

		Integer pageIndex = 1;
		Integer pageSize = 10;

		List<Message> messages = (List<Message>) jsonFileReaderUtil.loadTestData("src/test/resources/mock/message/entity-data-list.json", new TypeReference<List<Message>>() {});
		Page<Message> messagepages = new PageImpl<>(messages, SessionHelper.getPageRequest(pageIndex, pageSize), messages.size());
		List<MessageType> messageTypes = (List<MessageType>) jsonFileReaderUtil.loadTestData("src/test/resources/mock/message/model-data-list.json", new TypeReference<List<MessageType>>() {});
		MessageType messageModel =  messageTypes.stream().findFirst().orElse(null);		
		Message message = messages.stream().findFirst().orElse(null);

		// when
		when(sessionService.findByUserIdAndSessionId(user, sessionId)).thenReturn(session);
		when(messageRepository.findAllBySession(session, SessionHelper.getPageRequest(pageIndex, pageSize))).thenReturn(messagepages);
		when(messageMapper.toModel(message)).thenReturn(messageModel);

		// then
		assertThrows(ConstraintViolationException.class, () -> underTest.findAll(sessionId, user, pageIndex, pageSize));	
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
		ApiUserType user = (ApiUserType) jsonFileReaderUtil.loadTestData("src/test/resources/mock/apiuser/api-user.json",ApiUserType.class);
		Session session = (Session) jsonFileReaderUtil.loadTestData("src/test/resources/mock/session/entity-data.json",Session.class);

		String sessionId = session.getSessionId();

		Integer pageIndex = -1;
		Integer pageSize = 10;

		List<Message> messages = (List<Message>) jsonFileReaderUtil.loadTestData("src/test/resources/mock/message/entity-data-list.json", new TypeReference<List<Message>>() {});
		List<MessageType> messageTypes = (List<MessageType>) jsonFileReaderUtil.loadTestData("src/test/resources/mock/message/model-data-list.json", new TypeReference<List<MessageType>>() {});
		MessageType messageModel =  messageTypes.stream().findFirst().orElse(null);		
		Message message = messages.stream().findFirst().orElse(null);

		// when
		when(sessionService.findByUserIdAndSessionId(user, sessionId)).thenReturn(session);
		when(messageMapper.toModel(message)).thenReturn(messageModel);

		// then
		assertThrows(ConstraintViolationException.class, () -> underTest.findAll(sessionId, user, pageIndex, pageSize));	
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
		ApiUserType user = (ApiUserType) jsonFileReaderUtil.loadTestData("src/test/resources/mock/apiuser/api-user.json",ApiUserType.class);
		Session session = (Session) jsonFileReaderUtil.loadTestData("src/test/resources/mock/session/entity-data.json",Session.class);

		String sessionId = session.getSessionId();

		Integer pageIndex = 1;
		Integer pageSize = -1;

		List<Message> messages = (List<Message>) jsonFileReaderUtil.loadTestData("src/test/resources/mock/message/entity-data-list.json", new TypeReference<List<Message>>() {});
		List<MessageType> messageTypes = (List<MessageType>) jsonFileReaderUtil.loadTestData("src/test/resources/mock/message/model-data-list.json", new TypeReference<List<MessageType>>() {});
		MessageType messageModel =  messageTypes.stream().findFirst().orElse(null);		
		Message message = messages.stream().findFirst().orElse(null);

		// when
		when(sessionService.findByUserIdAndSessionId(user, sessionId)).thenReturn(session);
		when(messageMapper.toModel(message)).thenReturn(messageModel);

		// then
		assertThrows(ConstraintViolationException.class, () -> underTest.findAll(sessionId, user, pageIndex, pageSize));	
	}

}