package com.northbay.message.aspect;

import static org.apache.commons.lang3.ObjectUtils.isNotEmpty;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import com.northbay.message.model.MessageType;
import com.northbay.message.service.MessageService;

import lombok.RequiredArgsConstructor;

@Aspect
@Component("MessageTraceLog")
@RequiredArgsConstructor
@Profile("!test")
public class TraceLogAspect {

	private static final Logger LOG = LoggerFactory.getLogger(MessageService.class);

	@Before(value = "execution(* com.northbay.message.service.MessageService.create(..))")
	public void onPreCreate(JoinPoint joinPoint) {
		Object[] args = joinPoint.getArgs();
		if (isNotEmpty(args))
			LOG.info("Starting create message for sessionId={}", args[1]);
	}
	
	@AfterReturning(pointcut = "execution(* com.northbay.message.service.MessageService.create(..))", returning = "result")
	public void onPostCreate(MessageType result) {
		LOG.info("Finished create message for successfully");
	}
	
	
	@Before(value = "execution(* com.northbay.message.service.MessageService.findAll(..))")
	public void onPreFindAll(JoinPoint joinPoint) {
		Object[] args = joinPoint.getArgs();
		if (isNotEmpty(args))
			LOG.info("Starting find all message for sessionId={}, pageIndex={} and pageSize={}", args[0], args[1]);
	}
	
	@AfterReturning(pointcut = "execution(* com.northbay.message.service.MessageService.findAll(..))", returning = "result")
	public void onPostFindAll(Page<MessageType> result) {
		LOG.info("Finished find all messages count={} successfully", result.getSize());
	}	
}
