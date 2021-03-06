package com.plugwine.domain.model;

// Generated Jun 9, 2014 11:41:12 AM by Hibernate Tools 3.4.0.CR1

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * StageType generated by hbm2java
 */
public class StageType implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6688005033933372880L;
	private int id;
	private Serializable name;
	private int statusId;
	private boolean isDeleted;
	private Set<Stage> stages = new HashSet<Stage>(0);

	public StageType() {
	}

	public StageType(int id, Serializable name, int statusId, boolean isDeleted) {
		this.id = id;
		this.name = name;
		this.statusId = statusId;
		this.isDeleted = isDeleted;
	}

	public StageType(int id, Serializable name, int statusId,
			boolean isDeleted, Set<Stage> stages) {
		this.id = id;
		this.name = name;
		this.statusId = statusId;
		this.isDeleted = isDeleted;
		this.stages = stages;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Serializable getName() {
		return this.name;
	}

	public void setName(Serializable name) {
		this.name = name;
	}

	public int getStatusId() {
		return this.statusId;
	}

	public void setStatusId(int statusId) {
		this.statusId = statusId;
	}

	public boolean isIsDeleted() {
		return this.isDeleted;
	}

	public void setIsDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	public Set<Stage> getStages() {
		return this.stages;
	}

	public void setStages(Set<Stage> stages) {
		this.stages = stages;
	}

}
