package com.northbay.common.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
public class BaseEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3764109019513968589L;

	@Column(name = "created_at", updatable = false, columnDefinition = "TIMESTAMP")
	protected Date createdAt;

	@Column(name = "updated_at", columnDefinition = "TIMESTAMP")
	protected Date updatedAt;

	@PrePersist
	public void onInsert() {
		createdAt = new Date();
	}

	@PreUpdate
	public void onUpdate() {
		updatedAt = new Date();
	}

}
