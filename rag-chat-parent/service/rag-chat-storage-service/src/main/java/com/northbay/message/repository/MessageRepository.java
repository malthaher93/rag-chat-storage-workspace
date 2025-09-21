package com.northbay.message.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.northbay.message.entity.Message;
import com.northbay.session.entity.Session;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

	Page<Message> findAllBySession(Session session, Pageable pageable);
}
