package com.northbay.session.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.northbay.session.entity.Session;

@Repository
public interface SessionRepository extends JpaRepository<Session, Long> {

	Page<Session> findAllByUserId(String userId, Pageable pageable);
	
	Optional<Session> findByUserIdAndSessionId(String userId, String sessionId);
}
