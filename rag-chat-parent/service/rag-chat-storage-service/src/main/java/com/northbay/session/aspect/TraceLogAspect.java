package com.northbay.session.aspect;

import static org.apache.commons.lang3.ObjectUtils.isNotEmpty;

import java.util.stream.Stream;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import com.northbay.model.GenericResponseType;
import com.northbay.session.model.SessionType;
import com.northbay.session.service.SessionService;

import lombok.RequiredArgsConstructor;

@Aspect
@Component("SessionTraceLog")
@RequiredArgsConstructor
public class TraceLogAspect {

	private static final Logger LOG = LoggerFactory.getLogger(SessionService.class);


	@Before(value = "execution(* com.northbay.session.service.SessionService.create(..))")
	public void onPreCreate(JoinPoint joinPoint) {
		SessionType request = (SessionType) Stream.of(joinPoint.getArgs()).filter(arg -> arg instanceof SessionType).findFirst().orElse(null);
		if (isNotEmpty(request))
			LOG.info("Starting create sessoin for title={}, userId={}", request.getTitle(), request.getUserId());
	}
	
	@AfterReturning(pointcut = "execution(* com.northbay.session.service.SessionService.create(..))", returning = "result")
	public void onPostCreate(SessionType result) {
		LOG.info("Finished create session for title={}, sessionID={} successfully", result.getTitle(), result.getId());
	}
	
	
	@Before(value = "execution(* com.northbay.session.service.SessionService.findAll(..))")
	public void onPreFindAll(JoinPoint joinPoint) {
		Object[] args = joinPoint.getArgs();
		if (isNotEmpty(args))
			LOG.info("Starting find all sessoins for pageIndex={}, pageSize={}", args[0], args[1]);
	}
	
	@AfterReturning(pointcut = "execution(* com.northbay.session.service.SessionService.findAll(..))", returning = "result")
	public void onPostFindAll(Page<SessionType> result) {
		LOG.info("Finished find all sessions count={} successfully", result.getSize());
	}
	
	
	@Before(value = "execution(* com.northbay.session.service.SessionService.findById(..))")
	public void onPreFindById(JoinPoint joinPoint) {
		Object[] args = joinPoint.getArgs();
		if (isNotEmpty(args))
			LOG.info("Starting find session by id={}", args[0]);
	}
	
	@AfterReturning(pointcut = "execution(* com.northbay.session.service.SessionService.findById(..))", returning = "result")
	public void onPostFindById(SessionType result) {
		LOG.info("Finished find session by id={} successfully", result.getId());
	}

	
	@Before(value = "execution(* com.northbay.session.service.SessionService.update(..))")
	public void onPreUpdate(JoinPoint joinPoint) {
		Object[] args = joinPoint.getArgs();
		if (isNotEmpty(args))
			LOG.info("Starting update session by id={}", args[0]);
	}
	
	@AfterReturning(pointcut = "execution(* com.northbay.session.service.SessionService.update(..))", returning = "result")
	public void onPostUpdate(SessionType result) {
		LOG.info("Finished update session by id={} successfully", result.getId());
	}
	
	@Before(value = "execution(* com.northbay.session.service.SessionService.delete(..))")
	public void onPreDelete(JoinPoint joinPoint) {
		Object[] args = joinPoint.getArgs();
		if (isNotEmpty(args))
			LOG.info("Starting delete session by id={}", args[0]);
	}
	
	@AfterReturning(pointcut = "execution(* com.northbay.session.service.SessionService.delete(..))", returning = "result")
	public void onPostDelete(GenericResponseType<?>  result) {
		LOG.info("Finished delete session by id successfully");
	}
	
	
	@Before(value = "execution(* com.northbay.session.service.SessionService.toggleFavorite(..))")
	public void onPreToggle(JoinPoint joinPoint) {
		Object[] args = joinPoint.getArgs();
		if (isNotEmpty(args))
			LOG.info("Starting toggle favorite session by id={}", args[0]);
	}
	
	@AfterReturning(pointcut = "execution(* com.northbay.session.service.SessionService.toggleFavorite(..))", returning = "result")
	public void onPostToggle(SessionType result) {
		LOG.info("Finished toggle favorite session by id={} successfully", result.getId());
	}
}
