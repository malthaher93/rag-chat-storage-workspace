package com.northbay.message.entity;

import static javax.persistence.EnumType.STRING;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.northbay.common.entity.BaseEntity;
import com.northbay.message.enums.SenderCategoryType;
import com.northbay.session.entity.Session;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "message", indexes = @Index(columnList = "session_seq_id"))
@DynamicUpdate
@DynamicInsert
@Getter
@Setter
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Message extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9090903212416370906L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "message_seq")
	@SequenceGenerator(name = "message_seq", sequenceName = "message_seq", initialValue = 1, allocationSize = 1)
	@Column(name = "message_id", updatable = false)
	private Long id;
	
	@Column(name = "message_sender", updatable = false, nullable = false)
	@Enumerated(STRING)
	private SenderCategoryType sender;

	@Column(name = "message_content", updatable = false)
	private String content;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "session_seq_id", nullable = false)
	private Session session;

}
