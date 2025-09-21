package com.northbay.session.entity;

import java.util.Collection;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.northbay.common.entity.BaseEntity;
import com.northbay.message.entity.Message;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "session", 
indexes = {@Index(columnList = "user_id"), @Index(columnList = "user_id, session_id")})
@DynamicUpdate
@DynamicInsert
@Getter
@Setter
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Session extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9090903212416370906L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "session_seq")
	@SequenceGenerator(name = "session_seq", sequenceName = "session_seq", initialValue = 1, allocationSize = 1)
	@Column(name = "session_seq_id", updatable = false)
	private Long id;
	
	@Column(name = "session_id", unique = true, updatable = false, nullable = false)
	private String sessionId;
	
	@Column(name = "session_title", nullable = false)
	private String title;

	@Column(name = "is_favorite", columnDefinition = "boolean default false")
	private Boolean favorite;
	
	@Column(name = "user_id", nullable = false)
	private String userId;
	
	@OneToMany(mappedBy = "session", cascade = CascadeType.REMOVE)
	private Collection<Message> messageList;
	
}
