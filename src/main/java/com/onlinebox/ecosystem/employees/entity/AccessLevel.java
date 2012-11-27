package com.onlinebox.ecosystem.employees.entity;

import com.onlinebox.ecosystem.util.entity.*;
import java.io.*;
import javax.persistence.*;
@NamedQuery(name="AccessLevel.findAll", query="select l from AccessLevel l ORDER BY l.name")
@javax.persistence.EntityListeners(com.onlinebox.ecosystem.util.entity.DateUpdateListener.class)
@javax.persistence.Entity
@javax.persistence.Table(name="t_access_level")
public class AccessLevel implements IEntity, Serializable {

	@javax.persistence.Id
	@javax.persistence.GeneratedValue(strategy=GenerationType.IDENTITY)
	@javax.persistence.Column(name="Id", nullable=false, length=19)
	private long id;
	@javax.persistence.Column(name="Name", unique=true, nullable=false, length=60)
	private String name;
	@javax.persistence.Temporal(javax.persistence.TemporalType.TIMESTAMP)
	@javax.persistence.Column(name="CreateDate", nullable=false)
	private java.util.Date createDate;
	@javax.persistence.Temporal(javax.persistence.TemporalType.TIMESTAMP)
	@javax.persistence.Column(name="LastUpdateDate", nullable=false)
	private java.util.Date lastUpdateDate;

    @Override
	public long getId() {
		return this.id;
	}

    @Override
	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

    @Override
	public java.util.Date getCreateDate() {
		return this.createDate;
	}

    @Override
	public void setCreateDate(java.util.Date createDate) {
		this.createDate = createDate;
	}

    @Override
	public java.util.Date getLastUpdateDate() {
		return this.lastUpdateDate;
	}

    @Override
	public void setLastUpdateDate(java.util.Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

}